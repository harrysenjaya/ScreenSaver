package id.ac.unpar.screensaver.studentportal;

import id.ac.unpar.siamodels.JenisKelamin;
import id.ac.unpar.siamodels.Mahasiswa;
import id.ac.unpar.siamodels.Mahasiswa.Nilai;
import id.ac.unpar.siamodels.Semester;
import id.ac.unpar.siamodels.TahunSemester;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.ProtocolException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.SortedMap;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author harry
 */
public class Scraper {

    private final String BASE_URL = "https://studentportal.unpar.ac.id/";
    private final String LOGIN_URL = BASE_URL + "C_home/sso_login";
    private final String SSO_URL = "https://sso.unpar.ac.id/login";
    private final String JADWAL_URL = BASE_URL + "jadwal";
    private final String NILAI_URL = BASE_URL + "nilai";
    private final String TOEFL_URL = BASE_URL + "nilai/toefl";
    private final String LOGOUT_URL = BASE_URL + "logout";
    private final String HOME_URL = BASE_URL + "home";
    private final String PROFILE_URL = BASE_URL + "profil";
    private final String FRSPRS_URL = "https://restu.unpar.ac.id/frs_prs";

    public static final String[] MONTH_NAMES = {
        "Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"
    };

    public Scraper() throws FileNotFoundException, IOException {

    }

    public void init() throws IOException {
        Connection baseConn = Jsoup.connect(BASE_URL);
        baseConn.timeout(0);
        baseConn.method(Connection.Method.GET);
        baseConn.execute();
    }

    public String login(String npm, String pass) throws IOException {
        init();
        Mahasiswa logged_mhs = new Mahasiswa(npm);
        String user = logged_mhs.getEmailAddress();
        Connection conn = Jsoup.connect(LOGIN_URL);
        conn.data("Submit", "Login");
        conn.timeout(0);
        conn.method(Connection.Method.POST);
        Response resp = conn.execute();
        Document doc = resp.parse();
        String execution = doc.select("input[name=execution]").val();
        String jsessionid = resp.cookie("JSESSIONID");
        /* SSO LOGIN */
        Connection loginConn = Jsoup.connect(SSO_URL + ";jsessionid=" + jsessionid + "?service=" + LOGIN_URL);
        loginConn.cookies(resp.cookies());
        loginConn.data("username", user);
        loginConn.data("password", pass);
        loginConn.data("execution", execution);
        loginConn.data("_eventId", "submit");
        loginConn.timeout(0);
        loginConn.method(Connection.Method.POST);
        resp = loginConn.execute();
        if (resp.body().contains(user)) {
            Map<String, String> phpsessid = resp.cookies();
            return phpsessid.get("ci_session");
        } else {
            return null;
        }
    }

    public TahunSemester requestNamePhotoTahunSemester(String phpsessid, Mahasiswa mhs) throws IOException {
        Connection connection = Jsoup.connect(HOME_URL);
        connection.cookie("ci_session", phpsessid);
        connection.timeout(0);
        connection.method(Connection.Method.GET);
        Response resp = connection.execute();
        Document doc = resp.parse();
        String nama = doc.select("div[class=namaUser d-none d-lg-block mr-3]").text();
        mhs.setNama(nama.substring(0, nama.indexOf(mhs.getEmailAddress())));
        Element photo = doc.select("img[class=img-fluid fotoProfil]").first();
        String photoPath = photo.attr("src");
        mhs.setPhotoPath(photoPath);
        connection = Jsoup.connect(NILAI_URL);
        connection.cookie("ci_session", phpsessid);
        connection.timeout(0);
        connection.method(Connection.Method.GET);
        resp = connection.execute();
        doc = resp.parse();
        Elements curr_sem = doc.select("select[class=custom-select mr-3]");
        String[] sem_set = parseSemester(curr_sem.first().child(curr_sem.first().childrenSize() - 1).text());
        TahunSemester currTahunSemester = new TahunSemester(Integer.parseInt(sem_set[0]),
                Semester.fromString(sem_set[1]));
        return currTahunSemester;
    }

    public void requestNilai(String phpsessid, Mahasiswa logged_mhs) throws IOException, InterruptedException {
        Connection connection = Jsoup.connect(NILAI_URL);
        connection.cookie("ci_session", phpsessid);
        connection.timeout(0);
        connection.method(Connection.Method.POST);
        Response resp = connection.execute();
        Document doc = resp.parse();

        Elements dropdownSemester = doc.select("#dropdownSemester option");
        ArrayList<String> listSemester = new ArrayList<String>();
        for (Element semester : dropdownSemester) {
            listSemester.add(semester.attr("value"));
        }

        Thread[] threadUrl = new Thread[listSemester.size() - 1];
        for (int i = 0; i < listSemester.size() - 1; i++) {
            threadUrl[i] = new Thread(new MultipleRequest(i, listSemester, NILAI_URL, phpsessid, logged_mhs));
            threadUrl[i].start();
        }
        for (int i = 0; i < listSemester.size() - 1; i++) {
            threadUrl[i].join();
        }
        Collections.sort(logged_mhs.getRiwayatNilai(), new Comparator<Nilai>() {
            @Override
            public int compare(Nilai o1, Nilai o2) {
                if (o1.getTahunAjaran() < o2.getTahunAjaran()) {
                    return -1;
                }
                if (o1.getTahunAjaran() > o2.getTahunAjaran()) {
                    return +1;
                }
                if (o1.getSemester().getOrder() < o2.getSemester().getOrder()) {
                    return -1;
                }
                if (o1.getSemester().getOrder() > o2.getSemester().getOrder()) {
                    return +1;
                }
                return 0;
            }
        });
    }

    public void requestNilaiTOEFL(String phpsessid, Mahasiswa mahasiswa) throws IOException {
        SortedMap<LocalDate, Integer> nilaiTerakhirTOEFL = new TreeMap<>();
        Connection connection = Jsoup.connect(TOEFL_URL);
        connection.cookie("ci_session", phpsessid);
        connection.timeout(0);
        connection.method(Connection.Method.POST);
        Response resp = connection.execute();
        Document doc = resp.parse();
        Elements nilaiTOEFL = doc.select("table").select("tbody").select("tr");
        if (!nilaiTOEFL.isEmpty()) {
            for (int i = 0; i < nilaiTOEFL.size(); i++) {
                Element nilai = nilaiTOEFL.get(i).select("td").get(5);
                Element tgl_toefl = nilaiTOEFL.get(i).select("td").get(1);
                String[] tanggal = tgl_toefl.text().split("-");

                LocalDate localDate = LocalDate.of(Integer.parseInt(tanggal[2]), Integer.parseInt(tanggal[1]),
                        Integer.parseInt(tanggal[0]));

                nilaiTerakhirTOEFL.put(localDate, Integer.parseInt(nilai.text()));
            }
        }
        mahasiswa.setNilaiTOEFL(nilaiTerakhirTOEFL);
    }

    public void requestTanggalLahir(String phpsessid, Mahasiswa mahasiswa) throws IOException {
        Connection connection = Jsoup.connect(PROFILE_URL);
        connection.cookie("ci_session", phpsessid);
        connection.timeout(0);
        connection.method(Connection.Method.POST);
        Response resp = connection.execute();
        Document doc = resp.parse();

        Element elementTempatTanggalLahir = doc.select("div[class=offset-md-1 col-md-10 col-12 headerWrapper my-0 border-bottom]").first().children().get(1).children().get(1).select("h8").get(1);
        String tempatTanggalLahir = elementTempatTanggalLahir.text().substring(2);

        StringTokenizer tokenizer = new StringTokenizer(tempatTanggalLahir);
        int day = Integer.parseInt(tokenizer.nextToken());
        int month = Arrays.asList(MONTH_NAMES).indexOf(tokenizer.nextToken()) + 1;
        if (month < 0) {
            throw new ProtocolException("Month name not recognized in this date: " + tempatTanggalLahir);
        }
        int year = Integer.parseInt(tokenizer.nextToken());
        mahasiswa.setTanggalLahir(LocalDate.of(year, month, day));
    }

    public void logout() throws IOException {
        Connection logoutConn = Jsoup.connect(LOGOUT_URL);
        logoutConn.timeout(0);
        logoutConn.method(Connection.Method.GET);
        logoutConn.execute();
    }

    public String[] parseSemester(String sem_raw) {
        String[] sem_set = sem_raw.split("/")[0].split(" ");
        return new String[]{sem_set[1].trim(), sem_set[0].trim()};
    }

    public Mahasiswa[] requestMahasiswaList(String phpsessid, Mahasiswa mahasiswa) throws IllegalStateException, IOException, InterruptedException {
        if (phpsessid == null) {
            throw new IllegalStateException("Mohon login terlebih dahulu");
        }
        this.requestNamePhotoTahunSemester(phpsessid, mahasiswa);
        String file = "foto_harry.txt";

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String currentLine = reader.readLine();
        reader.close();

        System.out.println(currentLine);
        mahasiswa.setPhotoPath(currentLine);

        List<Mahasiswa> mahasiswaList;
        mahasiswaList = new ArrayList<>();
        mahasiswaList.add(mahasiswa);

        Mahasiswa dummy = new Mahasiswa("2017730001");
        dummy.setNama("DUMMY DATA");
        dummy.setJenisKelamin(JenisKelamin.PEREMPUAN);
        dummy.setTanggalLahir(LocalDate.of(1, 1, 1));
        this.requestMahasiswaDetail(phpsessid, dummy);
        mahasiswaList.add(dummy);
        Mahasiswa[] mahasiswaArray = new Mahasiswa[mahasiswaList.size()];
        for (int i = 0; i < mahasiswaArray.length; i++) {
            mahasiswaArray[i] = mahasiswaList.get(i);
        }
        return mahasiswaArray;
    }

    public Mahasiswa requestMahasiswaDetail(String phpsessid, Mahasiswa mahasiswa) throws IOException {
        try {
            this.requestNilaiTOEFL(phpsessid, mahasiswa);
            this.requestNilai(phpsessid, mahasiswa);
            this.requestTanggalLahir(phpsessid, mahasiswa);

        } catch (InterruptedException ex) {
            Logger.getLogger(Scraper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mahasiswa;
    }

}

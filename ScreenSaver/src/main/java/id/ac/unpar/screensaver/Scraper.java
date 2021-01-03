package id.ac.unpar.screensaver;

import id.ac.unpar.siamodels.Mahasiswa;
import id.ac.unpar.siamodels.TahunSemester;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;
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

    private final Properties credentials;
    private final String npm;
    private final String password;
    
    private Mahasiswa mahasiswa;
    private TahunSemester tahunSemester;
    
    public Scraper() throws FileNotFoundException, IOException {
        this.credentials = new Properties();
        this.credentials.load(new FileReader("login.properties"));
        this.npm = credentials.getProperty("user.npm");
        this.password = credentials.getProperty("user.password");
    }
    
     public void init() throws IOException {
        Connection baseConn = Jsoup.connect(BASE_URL);
        baseConn.timeout(0);
        baseConn.method(Connection.Method.GET);
        baseConn.execute();
    }
    
    public String login() throws IOException {
        init();
        this.mahasiswa = new Mahasiswa(this.npm);
        String user = this.mahasiswa.getEmailAddress();
        Connection conn = Jsoup.connect(LOGIN_URL);
        conn.data("Submit", "Login");
        conn.timeout(0);
        conn.method(Connection.Method.POST);
        Connection.Response resp = conn.execute();
        Document doc = resp.parse();
        String execution = doc.select("input[name=execution]").val();

        /* SSO LOGIN */
        Connection loginConn = Jsoup.connect(SSO_URL + "?service=" + LOGIN_URL);
        loginConn.data("username", user);
        loginConn.data("password", this.password);
        loginConn.data("execution", execution);
        loginConn.data("_eventId", "submit");
        loginConn.timeout(0);
        loginConn.method(Connection.Method.POST);
        resp = loginConn.execute();
        if (resp.body().contains(user)) {
            Map<String, String> sessionId = resp.cookies();
            return sessionId.get("ci_session");
        } else {
            return null;
        }
    }

    public void requestNamePhotoTahunSemester(String phpsessid) throws IOException {
       Connection connection = Jsoup.connect(HOME_URL);
        connection.cookie("ci_session", phpsessid);
        connection.timeout(0);
        connection.method(Connection.Method.GET);
        Connection.Response resp = connection.execute();
        
        Document doc = resp.parse();
        String nama = doc.select("div[class=namaUser d-none d-lg-block mr-3]").text();
        this.mahasiswa.setNama(nama.substring(0, nama.indexOf(this.mahasiswa.getEmailAddress())));
        
        Element photo = doc.select("img[class=img-fluid fotoProfil]").first();
        String photoPath = photo.attr("src");
        this.mahasiswa.setPhotoPath(photoPath);		
        connection = Jsoup.connect(NILAI_URL);
        connection.cookie("ci_session", phpsessid);
        connection.timeout(0);
        connection.method(Connection.Method.GET);
        resp = connection.execute();
        doc = resp.parse();		
        Elements options = doc.getElementsByAttributeValue("name", "dropdownSemester").first().children();   
        String curr_sem = options.last().val(); 
        curr_sem = curr_sem.substring(2,4).concat(curr_sem.substring(5));
        this.tahunSemester = new TahunSemester(curr_sem);
    } 

    public void requestNilaiTOEFL(String phpsessid) throws IOException {
        SortedMap<LocalDate, Integer> nilaiTerakhirTOEFL = new TreeMap<>();
        Connection connection = Jsoup.connect(TOEFL_URL);
        connection.cookie("ci_session", phpsessid);
        connection.timeout(0);
        connection.validateTLSCertificates(false);
        connection.method(Connection.Method.POST);
        Response resp = connection.execute();
        Document doc = resp.parse();
        Elements nilaiTOEFL = doc.select("table").select("tbody").select("tr");
        if (!nilaiTOEFL.isEmpty()) {
            for (int i = 0; i < nilaiTOEFL.size(); i++) {
                Element nilai = nilaiTOEFL.get(i).select("td").get(5);
                Element tgl_toefl = nilaiTOEFL.get(i).select("td").get(1);
                String[] tanggal = tgl_toefl.text().split(" ");
                switch (tanggal[1].toLowerCase()) {
                case "januari":
                        tanggal[1] = "1";
                        break;
                case "februari":
                        tanggal[1] = "2";
                        break;
                case "maret":
                        tanggal[1] = "3";
                        break;
                case "april":
                        tanggal[1] = "4";
                        break;
                case "mei":
                        tanggal[1] = "5";
                        break;
                case "juni":
                        tanggal[1] = "6";
                        break;
                case "juli":
                        tanggal[1] = "7";
                        break;
                case "agustus":
                        tanggal[1] = "8";
                        break;
                case "september":
                        tanggal[1] = "9";
                        break;
                case "oktober":
                        tanggal[1] = "10";
                        break;
                case "november":
                        tanggal[1] = "11";
                        break;
                case "desember":
                        tanggal[1] = "12";
                        break;
                }

                LocalDate localDate = LocalDate.of(Integer.parseInt(tanggal[2]), Integer.parseInt(tanggal[1]),
                                Integer.parseInt(tanggal[0]));

                nilaiTerakhirTOEFL.put(localDate, Integer.parseInt(nilai.text()));
            }
        }
        mahasiswa.setNilaiTOEFL(nilaiTerakhirTOEFL);
	}
    
    public Mahasiswa getMahasiswa() {
        return mahasiswa;
    }

    public TahunSemester getTahunSemester() {
        return tahunSemester;
    }
    
    
}

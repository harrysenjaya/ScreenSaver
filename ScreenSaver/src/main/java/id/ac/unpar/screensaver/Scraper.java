package id.ac.unpar.screensaver;

import id.ac.unpar.siamodels.Mahasiswa;
import id.ac.unpar.siamodels.TahunSemester;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import org.jsoup.Connection;
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
        connection.validateTLSCertificates(false);
        connection.method(Connection.Method.GET);
        Response resp = connection.execute();
        Document doc = resp.parse();
        String nama = doc.select("div[class=namaUser d-none d-lg-block mr-3]").text();
        mhs.setNama(nama.substring(0, nama.indexOf(mhs.getEmailAddress())));
        Element photo = doc.select("img[class=img-fluid  fotoProfil]").first();
        String photoPath = photo.attr("src");
        mhs.setPhotoPath(photoPath);
        connection = Jsoup.connect(FRSPRS_URL);
        connection.cookie("ci_session", phpsessid);
        connection.timeout(0);
        connection.validateTLSCertificates(false);
        connection.method(Connection.Method.GET);
        resp = connection.execute();
        doc = resp.parse();
        String curr_sem = doc.select(".custom-selectContent span").text();
        String[] sem_set = parseSemester(curr_sem);
        TahunSemester currTahunSemester = new TahunSemester(Integer.parseInt(sem_set[0]),
                        Semester.fromString(sem_set[1]));
        return currTahunSemester;
    } 

    public Mahasiswa getMahasiswa() {
        return mahasiswa;
    }

    public TahunSemester getTahunSemester() {
        return tahunSemester;
    }
    
    
}

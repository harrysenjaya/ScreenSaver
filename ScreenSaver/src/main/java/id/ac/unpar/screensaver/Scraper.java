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
        Mahasiswa logged_mhs = new Mahasiswa(npm);
        String user = logged_mhs.getEmailAddress();
        Connection conn = Jsoup.connect(LOGIN_URL);
        conn.data("Submit", "Login");
        conn.timeout(0);
        conn.validateTLSCertificates(false);
        conn.method(Connection.Method.POST);
        Response resp = conn.execute();
        Document doc = resp.parse();
        String lt = doc.select("input[name=lt]").val();
        String execution = doc.select("input[name=execution]").val();
        String jsessionid = resp.cookie("JSESSIONID");
        /* SSO LOGIN */
        Connection loginConn = Jsoup.connect(SSO_URL + ";jsessionid=" + jsessionid + "?service=" + LOGIN_URL);
        loginConn.cookies(resp.cookies());
        loginConn.data("username", user);
        loginConn.data("password", pass);
        loginConn.data("lt", lt);
        loginConn.data("execution", execution);
        loginConn.data("_eventId", "submit");
        loginConn.data("submit", "");
        loginConn.timeout(0);
        loginConn.validateTLSCertificates(false);
        loginConn.method(Connection.Method.POST);
        resp = loginConn.execute();
        if (resp.body().contains(user)) {
                Map<String, String> phpsessid = resp.cookies();
                return phpsessid.get("ci_session");
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

    public Mahasiswa getMahasiswa() {
        return mahasiswa;
    }

    public TahunSemester getTahunSemester() {
        return tahunSemester;
    }
    
    
}

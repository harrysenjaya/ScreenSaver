/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.unpar.screensaver;

import id.ac.unpar.siamodels.Mahasiswa;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

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

    private Properties credentials;
    private String npm;
    private String password;
    
    public Scraper() throws FileNotFoundException, IOException {
        this.credentials = new Properties();
        credentials.load(new FileReader("login.properties"));
        this.npm = credentials.getProperty("user.email");
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
        Mahasiswa mahasiswa = new Mahasiswa(this.npm);
        String user = mahasiswa.getEmailAddress();
        Connection conn = Jsoup.connect(LOGIN_URL);
        conn.data("Submit", "Login");
        conn.timeout(0);
        conn.method(Connection.Method.POST);
        Connection.Response resp = conn.execute();
        Document doc = resp.parse();
        String execution = doc.select("input[name=execution]").val();
//	String jsessionid = resp.cookie("JSESSIONID");

        /* SSO LOGIN */
        Connection loginConn = Jsoup.connect(SSO_URL + "?service=" + LOGIN_URL);
//	loginConn.cookies(resp.cookies());
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
}

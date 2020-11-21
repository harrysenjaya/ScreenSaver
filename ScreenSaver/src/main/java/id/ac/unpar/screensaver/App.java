package id.ac.unpar.screensaver;

import id.ac.unpar.siamodels.Mahasiswa;
import java.io.FileReader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class App extends Application {

    private static Scene scene;

    private final String BASE_URL = "https://studentportal.unpar.ac.id/";
    private final String LOGIN_URL = BASE_URL + "C_home/sso_login";
    private final String SSO_URL = "https://sso.unpar.ac.id/login";
    private final String JADWAL_URL = BASE_URL + "jadwal";
    private final String NILAI_URL = BASE_URL + "nilai";
    private final String TOEFL_URL = BASE_URL + "nilai/toefl";
    private final String LOGOUT_URL = BASE_URL + "logout";
    private final String HOME_URL = BASE_URL + "home";
    
    @Override
    public void start(Stage stage) throws IOException {
        Properties credentials = new Properties();
        credentials.load(new FileReader("login.properties"));
        login(credentials.getProperty("user.email"),credentials.getProperty("user.password"));
                
        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {

        launch();
    }
    
    public void init() throws IOException {
        Connection baseConn = Jsoup.connect(BASE_URL);
        baseConn.timeout(0);
//        baseConn.validateTLSCertificates(false);
        baseConn.method(Connection.Method.GET);
        baseConn.execute();
    }
    
    public String login(String npm, String pass) throws IOException {
		init();
                Mahasiswa test = new Mahasiswa(npm);
//		Mahasiswa logged_mhs = new Mahasiswa(npm);
//		String user = logged_mhs.getEmailAddress();
                String user = npm+"@student.unpar.ac.id";
		Connection conn = Jsoup.connect(LOGIN_URL);
		conn.data("Submit", "Login");
		conn.timeout(0);
//		conn.validateTLSCertificates(false);
		conn.method(Connection.Method.POST);
		Connection.Response resp = conn.execute();
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
//		loginConn.validateTLSCertificates(false);
		loginConn.method(Connection.Method.POST);
		resp = loginConn.execute();
                System.out.println(resp);
                System.out.println(resp.body());
		if (resp.body().contains(user)) {
			Map<String, String> phpsessid = resp.cookies();
			return phpsessid.get("ci_session");
		} else {
			return null;
		}
	}

}
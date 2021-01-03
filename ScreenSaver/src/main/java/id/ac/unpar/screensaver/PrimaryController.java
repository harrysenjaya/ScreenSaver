package id.ac.unpar.screensaver;

import id.ac.unpar.siamodels.Mahasiswa;
import id.ac.unpar.siamodels.TahunSemester;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

public class PrimaryController implements Initializable{
    private Scraper scraper;
    private String session;
    
    private Mahasiswa mahasiswa;
    private TahunSemester tahunSemester;
    
    @FXML
    private Text nama, email, tahun, semester, toefl;

    public PrimaryController() throws IOException {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.scraper = new Scraper();
            this.session = this.scraper.login();   
            this.scraper.requestNamePhotoTahunSemester(this.session);
            this.scraper.requestNilaiTOEFL(this.session);
        } catch (IOException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.mahasiswa=this.scraper.getMahasiswa();
        this.tahunSemester = this.scraper.getTahunSemester();
        this.nama.setText(this.mahasiswa.getNama());
        this.email.setText(this.mahasiswa.getEmailAddress());
        this.tahun.setText(this.tahunSemester.getTahun()+"");
        this.semester.setText(this.tahunSemester.getSemester().toString());    
        this.toefl.setText(this.mahasiswa.getNilaiTOEFL().firstKey().toString() + " " + this.mahasiswa.getNilaiTOEFL().get(this.mahasiswa.getNilaiTOEFL().firstKey()).toString());
    }


}

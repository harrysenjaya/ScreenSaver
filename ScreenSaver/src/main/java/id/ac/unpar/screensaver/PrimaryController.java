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
    private TahunSemester angkatanMahasiswa;
    
    @FXML
    private Text nama, angkatan, usia, status, email, toefl, ipk, sks;

    public PrimaryController() throws IOException {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.scraper = new Scraper();
            this.session = this.scraper.login();   
            this.scraper.requestNamePhotoTahunSemester(this.session);
            this.scraper.requestNilaiTOEFL(this.session);
            this.scraper.requestNilai(this.session);
            this.scraper.requestTanggalLahir(this.session);
        } catch (IOException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.mahasiswa=this.scraper.getMahasiswa();
        this.nama.setText(this.mahasiswa.getNama());
        this.angkatan.setText(this.angkatan.getText()+this.mahasiswa.getTahunAngkatan());
        this.email.setText(this.email.getText()+this.mahasiswa.getEmailAddress());
        this.toefl.setText(this.toefl.getText()+this.mahasiswa.getNilaiTOEFL().get(this.mahasiswa.getNilaiTOEFL().firstKey()).toString());
        this.ipk.setText(this.ipk.getText()+Math.round(this.mahasiswa.calculateIPS() * 100.0) / 100.0+"/"+Math.round(this.mahasiswa.calculateIPKumulatif() * 100.0) / 100.0);
        this.sks.setText(this.sks.getText()+this.mahasiswa.calculateSKSLulus()+"/"+this.mahasiswa.calculateSKSTempuh(false));
                
    }


}

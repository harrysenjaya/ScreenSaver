package id.ac.unpar.screensaver;

import id.ac.unpar.screensaver.studentportal.Scraper;
import id.ac.unpar.screensaver.studentportal.StudentPortalDataPuller;
import id.ac.unpar.siamodels.Mahasiswa;
import id.ac.unpar.siamodels.TahunSemester;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

public class PrimaryController implements Initializable{
  
    @FXML
    private Text nama, angkatan, usia, status, email, toefl, ipk, sks;

    public PrimaryController() throws IOException {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            StudentPortalDataPuller puller = new StudentPortalDataPuller();
            Mahasiswa[] listMahasiswa = puller.pullMahasiswas();
            for(int i=0; i<listMahasiswa.length; i++){
                Mahasiswa mahasiswa = puller.pullMahasiswaDetail(listMahasiswa[i]);
                this.nama.setText(mahasiswa.getNama());
                this.angkatan.setText(angkatan.getText()+mahasiswa.getTahunAngkatan());
                this.usia.setText(usia.getText()+Period.between(mahasiswa.getTanggalLahir(), LocalDate.now()).getYears() + " tahun " + Period.between(mahasiswa.getTanggalLahir(), LocalDate.now()).getMonths()+ " bulan " + " (lahir " + mahasiswa.getTanggalLahir().toString()+ ")");
                this.status.setText(status.getText()+"Tidak Tersedia");
                this.email.setText(email.getText()+mahasiswa.getEmailAddress());
                this.toefl.setText(toefl.getText()+mahasiswa.getNilaiTOEFL().get(mahasiswa.getNilaiTOEFL().firstKey()).toString());
                this.ipk.setText(ipk.getText()+Math.round(mahasiswa.calculateIPS() * 100.0) / 100.0+"/"+Math.round(mahasiswa.calculateIPKumulatif() * 100.0) / 100.0);
                this.sks.setText(sks.getText()+mahasiswa.calculateSKSLulus()+"/"+mahasiswa.calculateSKSTempuh(false));            
            }
        } catch (IOException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalStateException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

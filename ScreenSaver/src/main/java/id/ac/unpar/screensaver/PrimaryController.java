package id.ac.unpar.screensaver;

import id.ac.unpar.screensaver.siakad.SIAkadDataPuller;
import id.ac.unpar.siamodels.Mahasiswa;
import id.ac.unpar.siamodels.TahunSemester;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class PrimaryController implements Initializable{
  
    @FXML
    private Text nama, angkatan, usia, status, email, toefl, ipk, sks;

    int i = 1;

    private Mahasiswa mahasiswa;
    
    public PrimaryController() throws IOException {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            SIAkadDataPuller puller = new SIAkadDataPuller();
            Mahasiswa[] listMahasiswa = puller.pullMahasiswas();
            mahasiswa = puller.pullMahasiswaDetail(listMahasiswa[0]);
            this.nama.setText(mahasiswa.getNama());
            this.angkatan.setText(mahasiswa.getTahunAngkatan()+"");
            if (mahasiswa.getTanggalLahir() != null) {
                this.usia.setText(Period.between(mahasiswa.getTanggalLahir(), LocalDate.now()).getYears() + " tahun " + Period.between(mahasiswa.getTanggalLahir(), LocalDate.now()).getMonths()+ " bulan " + " (lahir " + mahasiswa.getTanggalLahir().toString()+ ")");
            } else {
                this.usia.setText("Tidak Tersedia");
            }
            this.status.setText("Tidak Tersedia");
            this.email.setText(mahasiswa.getEmailAddress());
            if (mahasiswa.getNilaiTOEFL() != null && !mahasiswa.getNilaiTOEFL().isEmpty()) {
                this.toefl.setText(mahasiswa.getNilaiTOEFL().get(mahasiswa.getNilaiTOEFL().firstKey()).toString());
            } else {
                this.toefl.setText("Tidak Tersedia");
            }
            
            this.ipk.setText(Math.round(mahasiswa.calculateIPS() * 100.0) / 100.0+"/"+Math.round(mahasiswa.calculateIPKumulatif() * 100.0) / 100.0);
            this.sks.setText(+mahasiswa.calculateSKSLulus()+"/"+mahasiswa.calculateSKSTempuh(false));    
            Timeline timeline = new Timeline(
                    new KeyFrame(
                        Duration.seconds(10),
                        event -> {
                try {
                    mahasiswa = puller.pullMahasiswaDetail(listMahasiswa[i]);
                    this.nama.setText(mahasiswa.getNama());
                    this.angkatan.setText(mahasiswa.getTahunAngkatan()+"");
                    this.usia.setText(Period.between(mahasiswa.getTanggalLahir(), LocalDate.now()).getYears() + " tahun " + Period.between(mahasiswa.getTanggalLahir(), LocalDate.now()).getMonths()+ " bulan " + " (lahir " + mahasiswa.getTanggalLahir().toString()+ ")");
                    this.status.setText("Tidak Tersedia");
                    this.email.setText(mahasiswa.getEmailAddress());
                    this.toefl.setText(mahasiswa.getNilaiTOEFL().get(mahasiswa.getNilaiTOEFL().firstKey()).toString());
                    this.ipk.setText(Math.round(mahasiswa.calculateIPS() * 100.0) / 100.0+"/"+Math.round(mahasiswa.calculateIPKumulatif() * 100.0) / 100.0);
                    this.sks.setText(+mahasiswa.calculateSKSLulus()+"/"+mahasiswa.calculateSKSTempuh(false));    
                    i+=1;
                } catch (IOException ex) {
                    Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
                }
                        } 
                    )
                );
                timeline.setCycleCount(Animation.INDEFINITE);
                timeline.play();

        } catch (IOException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalStateException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

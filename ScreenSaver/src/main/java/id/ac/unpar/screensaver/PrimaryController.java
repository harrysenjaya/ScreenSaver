package id.ac.unpar.screensaver;

import id.ac.unpar.screensaver.studentportal.Scraper;
import id.ac.unpar.screensaver.studentportal.StudentPortalDataPuller;
import id.ac.unpar.siamodels.Mahasiswa;
import java.io.ByteArrayInputStream;
import javafx.scene.image.Image;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class PrimaryController implements Initializable {

    @FXML
    private Text nama, angkatan, usia, status, email, toefl, ipk, sks;

    @FXML
    private ImageView foto;

    private int indexOfMahasiswa;

    public int getIndexOfMahasiswa() {
        return indexOfMahasiswa;
    }

    public void setIndexOfMahasiswa(int indexOfMahasiswa) {
        this.indexOfMahasiswa = indexOfMahasiswa;
    }

    public PrimaryController() throws IOException {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.setIndexOfMahasiswa(1);
            StudentPortalDataPuller puller = new StudentPortalDataPuller();
            Mahasiswa[] listMahasiswa = puller.pullMahasiswas();
            listMahasiswa[0] = puller.pullMahasiswaDetail(listMahasiswa[0]);
            ByteArrayInputStream bais = new ByteArrayInputStream(listMahasiswa[0].getPhotoImage());
			Image image = new Image(bais);
            this.foto.setImage(image);
            this.foto.fitWidthProperty();
            this.foto.fitHeightProperty();
            this.nama.setText(listMahasiswa[0].getNama());
            this.angkatan.setText(listMahasiswa[0].getTahunAngkatan() + "");
            this.usia.setText(Period.between(listMahasiswa[0].getTanggalLahir(), LocalDate.now()).getYears() + " tahun " + Period.between(listMahasiswa[0].getTanggalLahir(), LocalDate.now()).getMonths() + " bulan " + " (lahir " + listMahasiswa[0].getTanggalLahir().toString() + ")");
            this.status.setText("Tidak Tersedia");
            this.email.setText(listMahasiswa[0].getEmailAddress());
            if (listMahasiswa[0].getNilaiTOEFL() != null && listMahasiswa[0].getNilaiTOEFL().size() > 0) {
                this.toefl.setText(listMahasiswa[0].getNilaiTOEFL().get(listMahasiswa[0].getNilaiTOEFL().firstKey()).toString());
            } else {
                this.toefl.setText("Tidak Tersedia");
            }
            this.ipk.setText(Math.round(listMahasiswa[0].calculateIPS() * 100.0) / 100.0 + "/" + Math.round(listMahasiswa[0].calculateIPKumulatif() * 100.0) / 100.0);
            this.sks.setText(+listMahasiswa[0].calculateSKSLulus() + "/" + listMahasiswa[0].calculateSKSTempuh(false));
            Timeline timeline = new Timeline(
                    new KeyFrame(
                            Duration.seconds(10),
                            event -> {
                                if (this.getIndexOfMahasiswa() == listMahasiswa.length) {
                                    this.setIndexOfMahasiswa(0);
                                } else {
                                    // listMahasiswa[this.getIndexOfMahasiswa()] = puller.pullMahasiswaDetail();
                                    this.nama.setText(listMahasiswa[this.getIndexOfMahasiswa()].getNama());
                                    this.angkatan.setText(listMahasiswa[this.getIndexOfMahasiswa()].getTahunAngkatan() + "");
                                    this.usia.setText(Period.between(listMahasiswa[this.getIndexOfMahasiswa()].getTanggalLahir(), LocalDate.now()).getYears() + " tahun " + Period.between(listMahasiswa[1].getTanggalLahir(), LocalDate.now()).getMonths() + " bulan " + " (lahir " + listMahasiswa[1].getTanggalLahir().toString() + ")");
                                    this.status.setText("Tidak Tersedia");
                                    this.email.setText(listMahasiswa[this.getIndexOfMahasiswa()].getEmailAddress());
                                    this.toefl.setText(listMahasiswa[this.getIndexOfMahasiswa()].getNilaiTOEFL().get(listMahasiswa[this.getIndexOfMahasiswa()].getNilaiTOEFL().firstKey()).toString());
                                    this.ipk.setText(Math.round(listMahasiswa[this.getIndexOfMahasiswa()].calculateIPS() * 100.0) / 100.0 + "/" + Math.round(listMahasiswa[this.getIndexOfMahasiswa()].calculateIPKumulatif() * 100.0) / 100.0);
                                    this.sks.setText(+listMahasiswa[this.getIndexOfMahasiswa()].calculateSKSLulus() + "/" + listMahasiswa[this.getIndexOfMahasiswa()].calculateSKSTempuh(false));
                                    this.setIndexOfMahasiswa(this.getIndexOfMahasiswa() + 1);
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

package id.ac.unpar.screensaver;

import id.ac.unpar.screensaver.studentportal.Scraper;
import id.ac.unpar.screensaver.studentportal.StudentPortalDataPuller;
import id.ac.unpar.siamodels.Mahasiswa;
import id.ac.unpar.siamodels.TahunSemester;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
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
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import sun.misc.BASE64Decoder;

public class PrimaryController implements Initializable {

    @FXML
    private Text nama, angkatan, usia, status, email, toefl, ipk, sks;

    @FXML
    private ImageView foto;

    int i = 1;

    public PrimaryController() throws IOException {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            StudentPortalDataPuller puller = new StudentPortalDataPuller();
            Mahasiswa[] listMahasiswa = puller.pullMahasiswas();
            listMahasiswa[0] = puller.pullMahasiswaDetail();
//            InputStream is = new ByteArrayInputStream(listMahasiswa[0].getPhotoImage());
//            BufferedImage newBi = ImageIO.read(is);
            byte[] fotoByte = listMahasiswa[0].getPhotoImage();
            ByteArrayInputStream bais = new ByteArrayInputStream(fotoByte);
            BufferedImage bi = ImageIO.read(bais);
            WritableImage image = SwingFXUtils.toFXImage(bi, null);
            this.foto.setImage(image);
            this.foto.fitWidthProperty();
            this.foto.fitHeightProperty();
            this.nama.setText(listMahasiswa[0].getNama());
            this.angkatan.setText(listMahasiswa[0].getTahunAngkatan() + "");
            this.usia.setText(Period.between(listMahasiswa[0].getTanggalLahir(), LocalDate.now()).getYears() + " tahun " + Period.between(listMahasiswa[0].getTanggalLahir(), LocalDate.now()).getMonths() + " bulan " + " (lahir " + listMahasiswa[0].getTanggalLahir().toString() + ")");
            this.status.setText("Tidak Tersedia");
            this.email.setText(listMahasiswa[0].getEmailAddress());
            if (listMahasiswa[0].getNilaiTOEFL().size() > 0) {
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
                                // listMahasiswa[1] = puller.pullMahasiswaDetail();
                                this.nama.setText(listMahasiswa[1].getNama());
                                this.angkatan.setText(listMahasiswa[1].getTahunAngkatan() + "");
                                this.usia.setText(Period.between(listMahasiswa[1].getTanggalLahir(), LocalDate.now()).getYears() + " tahun " + Period.between(listMahasiswa[1].getTanggalLahir(), LocalDate.now()).getMonths() + " bulan " + " (lahir " + listMahasiswa[1].getTanggalLahir().toString() + ")");
                                this.status.setText("Tidak Tersedia");
                                this.email.setText(listMahasiswa[1].getEmailAddress());
                                // this.toefl.setText(listMahasiswa[1].getNilaiTOEFL().get(listMahasiswa[1].getNilaiTOEFL().firstKey()).toString());
//                    this.ipk.setText(Math.round(listMahasiswa[1].calculateIPS() * 100.0) / 100.0+"/"+Math.round(listMahasiswa[1].calculateIPKumulatif() * 100.0) / 100.0);
//                    this.sks.setText(+listMahasiswa[1].calculateSKSLulus()+"/"+listMahasiswa[1].calculateSKSTempuh(false));
                                i += 1;
                            }
                    )
            );
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();

        } catch (IOException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalStateException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

package id.ac.unpar.screensaver;

//import id.ac.unpar.screensaver.siakad.SIAkadDataPuller;
import id.ac.unpar.screensaver.studentportal.StudentPortalDataPuller;
import id.ac.unpar.siamodels.Mahasiswa;
import id.ac.unpar.siamodels.TahunSemester;
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

    private int indexOfMahasiswa = 0;
    private Mahasiswa[] listMahasiswa;
    private DataPuller puller;

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
            puller = new StudentPortalDataPuller();
            listMahasiswa = puller.pullMahasiswas();
        } catch (IllegalStateException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (listMahasiswa != null) {
            try {
                listMahasiswa[this.getIndexOfMahasiswa()] = puller.pullMahasiswaDetail(listMahasiswa[this.getIndexOfMahasiswa()]);
                this.updateView(listMahasiswa[this.getIndexOfMahasiswa()]);
                this.setIndexOfMahasiswa(this.getIndexOfMahasiswa() + 1);
            } catch (IOException ex) {
                Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            updateView();
        }
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(5),
                        event -> {
                            if (listMahasiswa == null) {
                                updateView();
                                try {
                                    puller = new StudentPortalDataPuller();
                                    listMahasiswa = puller.pullMahasiswas();
                                } catch (IllegalStateException ex) {
                                    Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } else {
                                if (this.getIndexOfMahasiswa() == listMahasiswa.length) {
                                    this.setIndexOfMahasiswa(0);
                                } else {
                                    try {
                                        if (listMahasiswa[this.getIndexOfMahasiswa()].getTanggalLahir() == null) {
                                            listMahasiswa[this.getIndexOfMahasiswa()] = puller.pullMahasiswaDetail(listMahasiswa[this.getIndexOfMahasiswa()]);
                                        }
                                        this.updateView(listMahasiswa[this.getIndexOfMahasiswa()]);
                                        this.setIndexOfMahasiswa(this.getIndexOfMahasiswa() + 1);
                                    } catch (IOException ex) {
                                        Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
                                        updateView();
                                    }
                                }
                            }
                        }
                )
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }

    public void updateView() {
        this.foto.setVisible(false);
        this.nama.setText("Pastikan koneksi internet berfungsi dengan normal!");
        this.angkatan.setText("-");
        this.usia.setText("-");
        this.status.setText("-");
        this.email.setText("-");
        this.toefl.setText("-");
        this.ipk.setText("-");
        this.sks.setText("-");
    }

    public void updateView(Mahasiswa mahasiswa) throws IOException {
        System.out.println("Updating view with " + mahasiswa.getNama());
        if (mahasiswa.getPhotoPath() != null) {
            ByteArrayInputStream bais = new ByteArrayInputStream(mahasiswa.getPhotoImage());
            Image image = new Image(bais);
            this.foto.setImage(image);

            this.foto.setVisible(true);
        } else {
            this.foto.setVisible(false);
        }
        this.nama.setText(mahasiswa.getNama());
        this.angkatan.setText(mahasiswa.getTahunAngkatan() + "");
        this.usia.setText(Period.between(mahasiswa.getTanggalLahir(), LocalDate.now()).getYears() + " tahun " + Period.between(mahasiswa.getTanggalLahir(), LocalDate.now()).getMonths() + " bulan " + " (lahir " + mahasiswa.getTanggalLahir().toString() + ")");
        this.status.setText("Tidak Tersedia");
        this.email.setText(mahasiswa.getEmailAddress());
        if (mahasiswa.getNilaiTOEFL() != null && mahasiswa.getNilaiTOEFL().size() > 0) {
            this.toefl.setText(mahasiswa.getNilaiTOEFL().get(mahasiswa.getNilaiTOEFL().firstKey()).toString());
        } else {
            this.toefl.setText("Tidak Tersedia");
        }
        TahunSemester tahunSemesterTerakhir = null;
        for (Mahasiswa.Nilai nilai : mahasiswa.getRiwayatNilai()) {
            if (tahunSemesterTerakhir == null || nilai.getTahunSemester().compareTo(tahunSemesterTerakhir) > 0) {
                tahunSemesterTerakhir = nilai.getTahunSemester();
            }
        }
        this.ipk.setText(Math.round(mahasiswa.calculateIPS(tahunSemesterTerakhir) * 100.0) / 100.0 + "/" + Math.round(mahasiswa.calculateIPK() * 100.0) / 100.0);
        this.sks.setText(+mahasiswa.calculateSKSLulus() + "/" + mahasiswa.calculateSKSTempuh(false));
    }
}

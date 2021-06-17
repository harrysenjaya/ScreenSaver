package id.ac.unpar.screensaver.studentportal;

import id.ac.unpar.screensaver.DataPuller;
import id.ac.unpar.screensaver.PrimaryController;
import id.ac.unpar.siamodels.Mahasiswa;
import id.ac.unpar.siamodels.TahunSemester;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StudentPortalDataPuller extends DataPuller {

    private Scraper scraper;

    private final Properties credentials = new Properties();

    private Mahasiswa mahasiswa;
    private String session;

    public StudentPortalDataPuller() {
        try {
            this.credentials.load(new FileReader("login.properties"));
            String npm = credentials.getProperty("user.npm");
            String password = credentials.getProperty("user.password");
            this.mahasiswa = new Mahasiswa(npm);
            this.scraper = new Scraper();
            this.session = this.scraper.login(npm, password);
        } catch (IOException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Mahasiswa[] pullMahasiswas() {
        Mahasiswa[] mahasiswas = null;
        try {
            mahasiswas = this.scraper.requestMahasiswaList(this.session, this.mahasiswa);
        } catch (IllegalStateException ex) {
            Logger.getLogger(StudentPortalDataPuller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StudentPortalDataPuller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(StudentPortalDataPuller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mahasiswas;
    }
    
    @Override
    public Mahasiswa pullMahasiswaDetail(Mahasiswa m) {
        try {
            this.scraper.requestMahasiswaDetail(this.session, m);
        } catch (IOException ex) {
            Logger.getLogger(StudentPortalDataPuller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return m;
    }

}

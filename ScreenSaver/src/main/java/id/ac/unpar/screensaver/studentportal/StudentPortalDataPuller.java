/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.unpar.screensaver.studentportal;

import id.ac.unpar.screensaver.PrimaryController;
import id.ac.unpar.siamodels.Mahasiswa;
import id.ac.unpar.siamodels.TahunSemester;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author harry
 */
public class StudentPortalDataPuller {

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

    public Mahasiswa[] pullMahasiswas() throws IOException, IllegalStateException, InterruptedException {
        return this.scraper.requestMahasiswaList(this.session, this.mahasiswa);
    }

    public Mahasiswa pullMahasiswaDetail() throws IOException {
        return this.scraper.requestMahasiswaDetail(this.session,this.mahasiswa);
    }

}

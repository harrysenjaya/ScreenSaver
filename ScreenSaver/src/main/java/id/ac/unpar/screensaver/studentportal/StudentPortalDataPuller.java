/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.unpar.screensaver.studentportal;

import id.ac.unpar.screensaver.PrimaryController;
import id.ac.unpar.siamodels.Mahasiswa;
import id.ac.unpar.siamodels.TahunSemester;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author harry
 */
public class StudentPortalDataPuller {
    private Scraper scraper;

    public StudentPortalDataPuller() {
        try {
            this.scraper = new Scraper();
            this.scraper.login();   

        } catch (IOException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public Mahasiswa[] pullMahasiswas() throws IOException, IllegalStateException, InterruptedException {
        return scraper.requestMahasiswaList();
    }
    
    public Mahasiswa pullMahasiswaDetail(Mahasiswa m) throws IOException {
        scraper.requestMahasiswaDetail(m);
        return m;
    }
    
    
        
    
    
     
}

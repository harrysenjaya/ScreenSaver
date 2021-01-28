/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.unpar.screensaver.siakad;

import id.ac.unpar.siamodels.Mahasiswa;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author pascal
 */
public class SIAkadDataPuller {
    
    private final SIAkad siakad;
    
    public SIAkadDataPuller() throws FileNotFoundException, IOException {
        Properties auth = new Properties();
        auth.load(new FileReader("login-dosen.properties"));
        String username = auth.getProperty("username");
        String password = auth.getProperty("password");

        this.siakad = new SIAkad();
        this.siakad.login(username, password);
    }
    
    public Mahasiswa[] pullMahasiswas() throws IOException {
        return (Mahasiswa[])siakad.requestMahasiswaList().toArray();
    }
    
    public Mahasiswa pullMahasiswaDetail(Mahasiswa m) throws IOException {
        siakad.requestRiwayatNilai(m, false);
        return m;
    }
}
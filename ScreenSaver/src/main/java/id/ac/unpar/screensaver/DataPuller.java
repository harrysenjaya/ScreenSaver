/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.unpar.screensaver;

import id.ac.unpar.siamodels.Mahasiswa;

/**
 *
 * @author harry
 */
public abstract class DataPuller {
    
    public abstract Mahasiswa[] pullMahasiswas();
    public abstract Mahasiswa pullMahasiswaDetail(Mahasiswa m);
}

package id.ac.unpar.screensaver;

import id.ac.unpar.siamodels.Mahasiswa;

public abstract class DataPuller {
    
    public abstract Mahasiswa[] pullMahasiswas();
    public abstract Mahasiswa pullMahasiswaDetail(Mahasiswa m);
}

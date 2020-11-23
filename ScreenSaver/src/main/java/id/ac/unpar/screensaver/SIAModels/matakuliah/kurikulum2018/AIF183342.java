package id.ac.unpar.siamodels.matakuliah.kurikulum2018;

import id.ac.unpar.siamodels.InfoMataKuliah;
import id.ac.unpar.siamodels.MataKuliah;
import id.ac.unpar.siamodels.matakuliah.interfaces.*;
import id.ac.unpar.siamodels.Mahasiswa;

import java.util.List;

@InfoMataKuliah(nama = "Kewirausahaan Berbasis Teknologi", sks = 3)
public class AIF183342 extends MataKuliah implements HasPrasyarat{

    @Override
    public boolean checkPrasyarat(Mahasiswa mahasiswa, List<String> reasonsContainer) {
        boolean ok = true;
        int sksLulus = mahasiswa.calculateSKSLulus();
        if (sksLulus < 90) {
            reasonsContainer.add("SKS Lulus " + sksLulus + ", belum mencapai syarat minimal 90");
            return false;
        }
        return ok;
    }
}

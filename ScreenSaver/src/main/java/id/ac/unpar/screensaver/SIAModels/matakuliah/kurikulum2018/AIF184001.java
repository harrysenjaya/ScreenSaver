package id.ac.unpar.siamodels.matakuliah.kurikulum2018;

import id.ac.unpar.siamodels.InfoMataKuliah;
import id.ac.unpar.siamodels.MataKuliah;
import id.ac.unpar.siamodels.matakuliah.interfaces.*;
import id.ac.unpar.siamodels.Mahasiswa;

import java.util.List;

@InfoMataKuliah(nama = "Skripsi 1", sks = 3)
public class AIF184001 extends MataKuliah implements HasPrasyarat{

    @Override
    public boolean checkPrasyarat(Mahasiswa mahasiswa, List<String> reasonsContainer) {
        boolean ok = true;
        if(mahasiswa.getTahunAngkatan() >= 2017){
            if (!mahasiswa.hasLulusKuliah("AIF183002") && !mahasiswa.hasLulusKuliah("AIF182007")) {
                reasonsContainer.add("Tidak memenuhi prasyarat lulus AIF183002 atau AIF182007");
                ok = false;
            }
        } else {
            if (!mahasiswa.hasLulusKuliah("AIF183002")) {
                reasonsContainer.add("Tidak memenuhi prasyarat lulus AIF183002");
                ok = false;
            }
        }
        int sksLulus = mahasiswa.calculateSKSLulus();
        if (sksLulus < 108) {
            reasonsContainer.add("SKS Lulus " + sksLulus + ", belum mencapai syarat minimal 108");
            return false;
        }
        return ok;
    }
}

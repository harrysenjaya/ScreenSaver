package id.ac.unpar.siamodels.matakuliah.kurikulum2018;

import id.ac.unpar.siamodels.InfoMataKuliah;
import id.ac.unpar.siamodels.MataKuliah;
import id.ac.unpar.siamodels.matakuliah.interfaces.*;
import id.ac.unpar.siamodels.Mahasiswa;

import java.util.List;


@InfoMataKuliah(nama = "Tugas Akhir", sks = 8)
public class AIF184004 extends MataKuliah implements HasPrasyarat{

    @Override
    public boolean checkPrasyarat(Mahasiswa mahasiswa, List<String> reasonsContainer) {
        boolean ok = true;
        if(mahasiswa.getTahunAngkatan() >= 2017){
            if (!mahasiswa.hasLulusKuliah("AIF183002") && !mahasiswa.hasLulusKuliah("AIF182007")) {
                reasonsContainer.add("Tidak memenuhi prasyarat lulus AIF183002 atau AIF 182007");
                ok = false;
            }
        } else {
            if (!mahasiswa.hasLulusKuliah("AIF183002")) {
                reasonsContainer.add("Tidak memenuhi prasyarat lulus AIF183002");
                ok = false;
            }
        }

        int sksLulus = mahasiswa.calculateSKSLulus();
        if (sksLulus < 124) {
            reasonsContainer.add("SKS Lulus " + sksLulus + ", belum mencapai syarat minimal 124");
            return false;
        }
        return ok;
    }
}

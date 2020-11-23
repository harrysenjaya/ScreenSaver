package id.ac.unpar.siamodels.matakuliah.kurikulum2018;

import id.ac.unpar.siamodels.InfoMataKuliah;
import id.ac.unpar.siamodels.MataKuliah;
import id.ac.unpar.siamodels.matakuliah.interfaces.*;
import id.ac.unpar.siamodels.Mahasiswa;

import java.util.List;


@InfoMataKuliah(nama = "Skripsi 2", sks = 5)
public class AIF184002 extends MataKuliah implements HasPrasyarat{

    @Override
    public boolean checkPrasyarat(Mahasiswa mahasiswa, List<String> reasonsContainer) {
        if (mahasiswa.hasLulusKuliah("AIF184001") && mahasiswa.hasLulusKuliah("AIF182007")) {
            return true;
        } else if (mahasiswa.calculateSKSLulus() >= 124) {
            reasonsContainer.add("CATATAN: Mahasiswa harus mengambil juga AIF184001 (tempuh bersama)");
            return true;
        } else {
            reasonsContainer.add("Harus sudah mengambil AIF184001, AIF182007 dan lulus 124 SKS");
            return false;
        }
    }
}

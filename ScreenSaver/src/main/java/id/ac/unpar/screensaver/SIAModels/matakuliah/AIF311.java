package id.ac.unpar.siamodels.matakuliah;

import id.ac.unpar.siamodels.Mahasiswa;
import id.ac.unpar.siamodels.MataKuliah;
import id.ac.unpar.siamodels.InfoMataKuliah;
import id.ac.unpar.siamodels.matakuliah.interfaces.HasPraktikum;
import id.ac.unpar.siamodels.matakuliah.interfaces.HasPrasyarat;

import java.util.List;

/**
 * Kuliah Pemrograman Fungsional bertujuan untuk: 1. memperkenalkan paradigma
 * pemrograman fungsional, yaitu sebuah pemrograman yang didasarkan pada konsep
 * pemetaan dan fungsi matematika. Penyelesaian suatu masalah didasari atas
 * aplikasi dari fungsi-fungsi tersebut. 2. memberikan dasar-dasar pemrograman
 * fungsional dengan menggunakan bahasa fungsional Haskell.
 *
 * @author Cecilia E. Nugraheni (cheni@unpar.ac.id)
 */

@InfoMataKuliah(nama = "Pemrograman Fungsional", sks = 2)
public class AIF311 extends MataKuliah implements HasPrasyarat, HasPraktikum {

    @Override
    public boolean checkPrasyarat(Mahasiswa mahasiswa, List<String> reasonsContainer) {
        boolean ok = true;
        if (!mahasiswa.hasTempuhKuliah("AIF103")) {
            reasonsContainer.add("Tidak memenuhi prasyarat tempuh AIF103");
            ok = false;
        }
        return ok;
    }

}

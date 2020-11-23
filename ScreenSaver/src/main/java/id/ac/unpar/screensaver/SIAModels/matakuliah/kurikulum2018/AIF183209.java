package id.ac.unpar.siamodels.matakuliah.kurikulum2018;

import id.ac.unpar.siamodels.InfoMataKuliah;
import id.ac.unpar.siamodels.MataKuliah;
import id.ac.unpar.siamodels.matakuliah.interfaces.*;
import id.ac.unpar.siamodels.Mahasiswa;

import java.util.List;


@InfoMataKuliah(nama = "Pemrograman pada Perangkat Bergerak", sks = 3)
public class AIF183209 extends MataKuliah implements HasPraktikum, HasPrasyarat{

    @Override
    public boolean checkPrasyarat(Mahasiswa mahasiswa, List<String> reasonsContainer) {
        boolean ok = true;
        if (!mahasiswa.hasTempuhKuliah("AIF182100") && !mahasiswa.hasTempuhKuliah("AIF182210")) {
            reasonsContainer.add("Tidak memenuhi prasyarat tempuh AIF182100 atau AIF182210");
            ok = false;
        }
        return ok;
    }
}

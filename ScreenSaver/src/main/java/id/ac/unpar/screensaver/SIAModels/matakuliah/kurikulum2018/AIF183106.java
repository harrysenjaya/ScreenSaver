package id.ac.unpar.siamodels.matakuliah.kurikulum2018;

import id.ac.unpar.siamodels.InfoMataKuliah;
import id.ac.unpar.siamodels.MataKuliah;
import id.ac.unpar.siamodels.matakuliah.interfaces.*;
import id.ac.unpar.siamodels.Mahasiswa;

import java.util.List;


@InfoMataKuliah(nama = "Proyek Informatika", sks = 6)
public class AIF183106 extends MataKuliah implements HasPrasyarat{

    @Override
    public boolean checkPrasyarat(Mahasiswa mahasiswa, List<String> reasonsContainer) {
        boolean ok = true;
        if (!mahasiswa.hasTempuhKuliah("AIF183303") && !mahasiswa.hasTempuhKuliah("AIF183305")) {
            reasonsContainer.add("Tidak memenuhi prasyarat tempuh AIF183303 atau AIF183305");
            ok = false;
        }
        return ok;
    }
}

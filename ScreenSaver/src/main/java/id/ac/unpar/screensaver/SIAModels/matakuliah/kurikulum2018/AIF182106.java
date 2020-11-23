package id.ac.unpar.siamodels.matakuliah.kurikulum2018;

import id.ac.unpar.siamodels.InfoMataKuliah;
import id.ac.unpar.siamodels.MataKuliah;
import id.ac.unpar.siamodels.matakuliah.interfaces.*;
import id.ac.unpar.siamodels.Mahasiswa;

import java.util.List;


@InfoMataKuliah(nama = "Desain dan Analisis Algoritma", sks = 3)
public class AIF182106 extends MataKuliah implements HasPraktikum, HasResponsi, HasPrasyarat{

    @Override
    public boolean checkPrasyarat(Mahasiswa mahasiswa, List<String> reasonsContainer) {
        boolean ok = true;
        if (!mahasiswa.hasLulusKuliah("AIF182101")) {
            reasonsContainer.add("Tidak memenuhi prasyarat lulus AIF182101");
            ok = false;
        }
        if (!mahasiswa.hasTempuhKuliah("AIF182103")) {
            reasonsContainer.add("Tidak memenuhi prasyarat tempuh AIF182103");
            ok = false;
        }
        return ok;
    }
}

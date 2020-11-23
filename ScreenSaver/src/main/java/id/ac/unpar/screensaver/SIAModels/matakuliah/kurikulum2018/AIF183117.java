package id.ac.unpar.siamodels.matakuliah.kurikulum2018;

import id.ac.unpar.siamodels.InfoMataKuliah;
import id.ac.unpar.siamodels.MataKuliah;
import id.ac.unpar.siamodels.matakuliah.interfaces.*;
import id.ac.unpar.siamodels.Mahasiswa;

import java.util.List;


@InfoMataKuliah(nama = "Grafika Komputer", sks = 2)
public class AIF183117 extends MataKuliah implements HasPraktikum, HasPrasyarat{

    @Override
    public boolean checkPrasyarat(Mahasiswa mahasiswa, List<String> reasonsContainer) {
        boolean ok = true;
        if (!mahasiswa.hasLulusKuliah("AIF182105")) {
            reasonsContainer.add("Tidak memenuhi prasyarat lulus AIF182105");
            ok = false;
        }
        if (!mahasiswa.hasTempuhKuliah("AIF181103")) {
            reasonsContainer.add("Tidak memenuhi prasyarat tempuh AIF181103");
            ok = false;
        }
        return ok;
    }
}

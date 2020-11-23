package id.ac.unpar.siamodels.matakuliah.kurikulum2018;

import id.ac.unpar.siamodels.Mahasiswa;
import id.ac.unpar.siamodels.InfoMataKuliah;
import id.ac.unpar.siamodels.MataKuliah;
import id.ac.unpar.siamodels.matakuliah.interfaces.*;

import java.util.List;


@InfoMataKuliah(nama = "Teori Bahasa dan Kompilasi", sks = 2)
public class AIF183149 extends MataKuliah implements HasPraktikum, HasPrasyarat{

    @Override
    public boolean checkPrasyarat(Mahasiswa mahasiswa, List<String> reasonsContainer) {
        boolean ok = true;
        if (!mahasiswa.hasLulusKuliah("AIF181104") && !mahasiswa.hasLulusKuliah("AIF182103")) {
            reasonsContainer.add("Tidak memenuhi prasyarat lulus AIF181104 atau AIF182103");
            ok = false;
        }
        return ok;
    }
}

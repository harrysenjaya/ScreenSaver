package id.ac.unpar.siamodels.matakuliah.kurikulum2018;

import id.ac.unpar.siamodels.InfoMataKuliah;
import id.ac.unpar.siamodels.MataKuliah;
import id.ac.unpar.siamodels.matakuliah.interfaces.*;
import id.ac.unpar.siamodels.Mahasiswa;

import java.util.List;


@InfoMataKuliah(nama = "Rekayasa Perangkat Lunak", sks = 3)
public class AIF183303 extends MataKuliah implements HasResponsi, HasPrasyarat{

    @Override
    public boolean checkPrasyarat(Mahasiswa mahasiswa, List<String> reasonsContainer) {
        boolean ok = true;
        if (!mahasiswa.hasTempuhKuliah("AIF182100")) {
            reasonsContainer.add("Tidak memenuhi prasyarat tempuh AIF182100");
            ok = false;
        }
        return ok;
    }
}

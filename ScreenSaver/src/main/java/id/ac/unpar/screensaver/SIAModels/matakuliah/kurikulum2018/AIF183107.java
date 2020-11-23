package id.ac.unpar.siamodels.matakuliah.kurikulum2018;

import id.ac.unpar.siamodels.InfoMataKuliah;
import id.ac.unpar.siamodels.MataKuliah;
import id.ac.unpar.siamodels.matakuliah.interfaces.*;
import id.ac.unpar.siamodels.Mahasiswa;

import java.util.List;


@InfoMataKuliah(nama = "Pengantar Sistem Cerdas", sks = 3)
public class AIF183107 extends MataKuliah implements HasPrasyarat{

    @Override
    public boolean checkPrasyarat(Mahasiswa mahasiswa, List<String> reasonsContainer) {
        boolean ok = true;
        if (!mahasiswa.hasTempuhKuliah("AIF182106") && !mahasiswa.hasTempuhKuliah("AIF181104")) {
            reasonsContainer.add("Tidak memenuhi prasyarat tempuh AIF182106 atau AIF 181104");
            ok = false;
        }
        return ok;
    }
}

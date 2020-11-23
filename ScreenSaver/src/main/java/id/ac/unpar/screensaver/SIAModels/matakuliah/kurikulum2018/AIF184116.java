package id.ac.unpar.siamodels.matakuliah.kurikulum2018;

import id.ac.unpar.siamodels.InfoMataKuliah;
import id.ac.unpar.siamodels.MataKuliah;
import id.ac.unpar.siamodels.matakuliah.interfaces.*;
import id.ac.unpar.siamodels.Mahasiswa;

import java.util.List;


@InfoMataKuliah(nama = "Sistem Multi Agen", sks = 2)
public class AIF184116 extends MataKuliah implements HasPrasyarat{

    @Override
    public boolean checkPrasyarat(Mahasiswa mahasiswa, List<String> reasonsContainer) {
        boolean ok = true;
        if (!mahasiswa.hasTempuhKuliah("AIF183201") && !mahasiswa.hasTempuhKuliah("AIF183107")) {
            reasonsContainer.add("Tidak memenuhi prasyarat tempuh AIF183201 atau AIF183107");
            ok = false;
        }
        return ok;
    }
}

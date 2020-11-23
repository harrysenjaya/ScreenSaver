package id.ac.unpar.siamodels.matakuliah.kurikulum2018;

import id.ac.unpar.siamodels.InfoMataKuliah;
import id.ac.unpar.siamodels.MataKuliah;
import id.ac.unpar.siamodels.matakuliah.interfaces.*;
import id.ac.unpar.siamodels.Mahasiswa;

import java.util.List;


@InfoMataKuliah(nama = "Manajemen Proses Bisnis", sks = 3)
public class AIF184338 extends MataKuliah implements HasPrasyarat{

    @Override
    public boolean checkPrasyarat(Mahasiswa mahasiswa, List<String> reasonsContainer) {
        boolean ok = true;
        if (!mahasiswa.hasTempuhKuliah("AIF182105") && !mahasiswa.hasTempuhKuliah("AIF182204")) {
            reasonsContainer.add("Tidak memenuhi prasyarat tempuh AIF182105 atau AIF182204");
            ok = false;
        }
        return ok;
    }
}

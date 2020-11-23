package id.ac.unpar.siamodels.matakuliah.kurikulum2018;

import id.ac.unpar.siamodels.InfoMataKuliah;
import id.ac.unpar.siamodels.MataKuliah;
import id.ac.unpar.siamodels.matakuliah.interfaces.*;
import id.ac.unpar.siamodels.Mahasiswa;

import java.util.List;


@InfoMataKuliah(nama = "Layanan Berbasis Web", sks = 3)
public class AIF184235 extends MataKuliah implements HasPrasyarat{

    @Override
    public boolean checkPrasyarat(Mahasiswa mahasiswa, List<String> reasonsContainer) {
        boolean ok = true;
        if (!mahasiswa.hasLulusKuliah("AIF182204") && !mahasiswa.hasLulusKuliah("AIF182302") && !mahasiswa.hasLulusKuliah("AIF183204")) {
            reasonsContainer.add("Tidak memenuhi prasyarat lulus AIF182204, AIF182302 atau AIF183204");
            ok = false;
        }
        return ok;
    }
}

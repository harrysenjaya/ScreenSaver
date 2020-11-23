package id.ac.unpar.siamodels.matakuliah.kurikulum2018;

import id.ac.unpar.siamodels.InfoMataKuliah;
import id.ac.unpar.siamodels.MataKuliah;
import id.ac.unpar.siamodels.matakuliah.interfaces.*;
import id.ac.unpar.siamodels.Mahasiswa;

import java.util.List;


@InfoMataKuliah(nama = "Pemrograman Berbasis Web Lanjut", sks = 3)
public class AIF183232 extends MataKuliah implements HasPraktikum, HasPrasyarat{

    @Override
    public boolean checkPrasyarat(Mahasiswa mahasiswa, List<String> reasonsContainer) {
        boolean ok = true;
        if (!mahasiswa.hasLulusKuliah("AIF182204") && !mahasiswa.hasLulusKuliah("AIF182302")) {
            reasonsContainer.add("Tidak memenuhi prasyarat lulus AIF182204 atau AIF182302");
            ok = false;
        }
        return ok;
    }
}

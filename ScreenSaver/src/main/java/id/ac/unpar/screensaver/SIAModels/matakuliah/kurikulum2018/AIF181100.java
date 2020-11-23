package id.ac.unpar.siamodels.matakuliah.kurikulum2018;


import id.ac.unpar.siamodels.InfoMataKuliah;
import id.ac.unpar.siamodels.MataKuliah;
import id.ac.unpar.siamodels.matakuliah.interfaces.*;
import id.ac.unpar.siamodels.Mahasiswa;

import java.util.List;


@InfoMataKuliah(nama = "Dasar Pemrograman", sks = 4)
public class AIF181100 extends MataKuliah implements HasPraktikum, HasPrasyarat{

    @Override
    public boolean checkPrasyarat(Mahasiswa mahasiswa, List<String> reasonsContainer) {
        boolean ok = true;
        if(mahasiswa.getTahunAngkatan() >= 2018) {
            if (!mahasiswa.hasLulusKuliah("AIF181101")) {
                reasonsContainer.add("Tidak memenuhi prasyarat lulus AIF181101");
                ok = false;
            }
        }
        return ok;
    }
}

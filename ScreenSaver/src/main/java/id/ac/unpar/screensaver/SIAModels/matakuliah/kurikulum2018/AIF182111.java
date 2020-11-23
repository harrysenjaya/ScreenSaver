package id.ac.unpar.siamodels.matakuliah.kurikulum2018;

import id.ac.unpar.siamodels.Mahasiswa;
import id.ac.unpar.siamodels.InfoMataKuliah;
import id.ac.unpar.siamodels.MataKuliah;
import id.ac.unpar.siamodels.matakuliah.interfaces.*;

import java.util.List;


@InfoMataKuliah(nama = "Pemrograman Kompetitif 1", sks = 3)
public class AIF182111 extends MataKuliah implements HasPraktikum, HasPrasyarat{

    @Override
    public boolean checkPrasyarat(Mahasiswa mahasiswa, List<String> reasonsContainer) {
        boolean ok = true;
        if (!mahasiswa.hasLulusKuliah("AIF182101") && mahasiswa.getNilaiAkhirMataKuliah("AIF182101") < 2.0) {
            reasonsContainer.add("Tidak memenuhi prasyarat lulus AIF182101 (minimum C)");
            ok = false;
        }
        return ok;
    }
}

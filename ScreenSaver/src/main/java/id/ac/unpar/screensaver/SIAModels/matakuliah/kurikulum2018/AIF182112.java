package id.ac.unpar.siamodels.matakuliah.kurikulum2018;

import id.ac.unpar.siamodels.Mahasiswa;
import id.ac.unpar.siamodels.InfoMataKuliah;
import id.ac.unpar.siamodels.MataKuliah;
import id.ac.unpar.siamodels.matakuliah.interfaces.*;

import java.util.List;


@InfoMataKuliah(nama = "Pemrograman Kompetitif 2", sks = 3)
public class AIF182112 extends MataKuliah implements HasPraktikum, HasPrasyarat{

    @Override
    public boolean checkPrasyarat(Mahasiswa mahasiswa, List<String> reasonsContainer) {
        boolean ok = true;
        if (!mahasiswa.hasLulusKuliah("AIF182111") && mahasiswa.getNilaiAkhirMataKuliah("AIF182111") < 3.0) {
            reasonsContainer.add("Tidak memenuhi prasyarat lulus AIF182111 (minimum B)");
            ok = false;
        }
        return ok;
    }
}

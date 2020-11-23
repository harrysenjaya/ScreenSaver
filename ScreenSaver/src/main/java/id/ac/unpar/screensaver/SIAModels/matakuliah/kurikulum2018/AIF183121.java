package id.ac.unpar.siamodels.matakuliah.kurikulum2018;

import id.ac.unpar.siamodels.InfoMataKuliah;
import id.ac.unpar.siamodels.MataKuliah;
import id.ac.unpar.siamodels.matakuliah.interfaces.*;
import id.ac.unpar.siamodels.Mahasiswa;

import java.util.List;


@InfoMataKuliah(nama = "Pemrograman Kompetitif 3", sks = 3)
public class AIF183121 extends MataKuliah implements HasPraktikum, HasPrasyarat{

    @Override
    public boolean checkPrasyarat(Mahasiswa mahasiswa, List<String> reasonsContainer) {
        boolean ok = true;
        if (!mahasiswa.hasLulusKuliah("AIF182112") && mahasiswa.getNilaiAkhirMataKuliah("AIF182112") < 3.0) {
            reasonsContainer.add("Tidak memenuhi prasyarat lulus AIF182112 (minimum B)");
            ok = false;
        }
        return ok;
    }
}

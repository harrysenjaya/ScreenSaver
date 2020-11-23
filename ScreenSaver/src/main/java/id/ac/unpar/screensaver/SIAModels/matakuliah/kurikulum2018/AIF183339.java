package id.ac.unpar.siamodels.matakuliah.kurikulum2018;

import id.ac.unpar.siamodels.InfoMataKuliah;
import id.ac.unpar.siamodels.Mahasiswa;
import id.ac.unpar.siamodels.MataKuliah;
import id.ac.unpar.siamodels.matakuliah.interfaces.HasPraktikum;
import id.ac.unpar.siamodels.matakuliah.interfaces.HasPrasyarat;

import java.util.List;


@InfoMataKuliah(nama = "Sertifikasi Perancangan dan Pemrograman Basis Data dengan SQL", sks = 2)
public class AIF183339 extends MataKuliah implements HasPrasyarat, HasPraktikum {
    @Override
    public boolean checkPrasyarat(Mahasiswa mahasiswa, List<String> reasonsContainer) {
        boolean ok = true;
        if (!mahasiswa.hasLulusKuliah("AIF182112")) {
            reasonsContainer.add("Tidak memenuhi prasyarat lulus AIF182112");
            ok = false;
        }
        return ok;
    }
}

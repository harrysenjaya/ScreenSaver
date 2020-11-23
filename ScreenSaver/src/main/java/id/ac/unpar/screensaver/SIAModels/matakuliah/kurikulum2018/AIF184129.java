package id.ac.unpar.siamodels.matakuliah.kurikulum2018;

import id.ac.unpar.siamodels.InfoMataKuliah;
import id.ac.unpar.siamodels.MataKuliah;
import id.ac.unpar.siamodels.matakuliah.interfaces.*;
import id.ac.unpar.siamodels.Mahasiswa;

import java.util.List;


@InfoMataKuliah(nama = "Sertifikasi Administrasi Jaringan Komputer 3", sks = 3)
public class AIF184129 extends MataKuliah implements HasPrasyarat, HasPraktikum{

    @Override
    public boolean checkPrasyarat(Mahasiswa mahasiswa, List<String> reasonsContainer) {
        boolean ok = true;
        if (!mahasiswa.hasLulusKuliah("AIF183236")) {
            reasonsContainer.add("Tidak memenuhi prasyarat lulus AIF183236");
            ok = false;
        }
        return ok;
    }
}

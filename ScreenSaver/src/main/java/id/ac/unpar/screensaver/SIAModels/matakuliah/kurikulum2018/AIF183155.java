package id.ac.unpar.siamodels.matakuliah.kurikulum2018;

import id.ac.unpar.siamodels.Mahasiswa;
import id.ac.unpar.siamodels.InfoMataKuliah;
import id.ac.unpar.siamodels.MataKuliah;
import id.ac.unpar.siamodels.matakuliah.interfaces.*;

import java.util.List;


@InfoMataKuliah(nama = "Pemrograman Lojik", sks = 2)
public class AIF183155 extends MataKuliah implements HasPrasyarat{

    @Override
    public boolean checkPrasyarat(Mahasiswa mahasiswa, List<String> reasonsContainer) {
        boolean ok = true;
        if (!mahasiswa.hasLulusKuliah("AIF181104")) {
            reasonsContainer.add("Tidak memenuhi prasyarat lulus AIF181104");
            ok = false;
        }
        return ok;
    }
}

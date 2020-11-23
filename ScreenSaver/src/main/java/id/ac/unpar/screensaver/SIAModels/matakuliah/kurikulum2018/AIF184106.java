package id.ac.unpar.siamodels.matakuliah.kurikulum2018;

import id.ac.unpar.siamodels.InfoMataKuliah;
import id.ac.unpar.siamodels.MataKuliah;
import id.ac.unpar.siamodels.matakuliah.interfaces.HasPrasyarat;
import id.ac.unpar.siamodels.Mahasiswa;

import java.util.List;


@InfoMataKuliah(nama = "Analisis Data Permainan Komputer", sks = 3)
public class AIF184106 extends MataKuliah implements HasPrasyarat{

    @Override
    public boolean checkPrasyarat(Mahasiswa mahasiswa, List<String> reasonsContainer) {
        boolean ok = true;
        if (!mahasiswa.hasLulusKuliah("AIF184119") && mahasiswa.getNilaiAkhirMataKuliah("AIF184119") < 3.0) {
            reasonsContainer.add("Tidak memenuhi prasyarat lulus AIF184119 (minimum B)");
            ok = false;
        }
        return ok;
    }
}

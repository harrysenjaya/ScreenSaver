package id.ac.unpar.siamodels.matakuliah.kurikulum2018;

import id.ac.unpar.siamodels.InfoMataKuliah;
import id.ac.unpar.siamodels.MataKuliah;
import id.ac.unpar.siamodels.matakuliah.interfaces.HasPrasyarat;
import id.ac.unpar.siamodels.Mahasiswa;

import java.util.List;


@InfoMataKuliah(nama = "Perancangan Permainan Komputer", sks = 3)
public class AIF184119 extends MataKuliah implements HasPrasyarat{

    @Override
    public boolean checkPrasyarat(Mahasiswa mahasiswa, List<String> reasonsContainer) {
        boolean ok = true;
        if (!mahasiswa.hasLulusKuliah("AIF182100") && mahasiswa.getNilaiAkhirMataKuliah("AIF182100")< 3.0 && !mahasiswa.hasLulusKuliah("AIF183120") && mahasiswa.getNilaiAkhirMataKuliah("AIF183120") < 3.0) {
            reasonsContainer.add("Tidak memenuhi prasyarat lulus AIF183100 (minimum B) atau AIF 183120 (minimum B)");
            ok = false;
        }
        return ok;
    }
}

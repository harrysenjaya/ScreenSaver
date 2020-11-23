package id.ac.unpar.siamodels.matakuliah.kurikulum2018;

import id.ac.unpar.siamodels.InfoMataKuliah;
import id.ac.unpar.siamodels.MataKuliah;
import id.ac.unpar.siamodels.matakuliah.interfaces.HasPrasyarat;
import id.ac.unpar.siamodels.Mahasiswa;

import java.util.List;


@InfoMataKuliah(nama = "Pemrograman Permainan Komputer", sks = 3)
public class AIF183120 extends MataKuliah implements HasPrasyarat{

    @Override
    public boolean checkPrasyarat(Mahasiswa mahasiswa, List<String> reasonsContainer) {
        boolean ok = true;
        if (!mahasiswa.hasTempuhKuliah("AIF182101") && mahasiswa.getNilaiAkhirMataKuliah("AIF182101") < 3.0) {
            reasonsContainer.add("Tidak memenuhi prasyarat tempuh AIF182101 (minimum B)");
            ok = false;
        }
        return ok;
    }
}

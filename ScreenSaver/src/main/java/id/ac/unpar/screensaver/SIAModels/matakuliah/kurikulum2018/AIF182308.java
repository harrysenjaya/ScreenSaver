package id.ac.unpar.siamodels.matakuliah.kurikulum2018;

import id.ac.unpar.siamodels.InfoMataKuliah;
import id.ac.unpar.siamodels.MataKuliah;
import id.ac.unpar.siamodels.matakuliah.interfaces.HasPrasyarat;
import id.ac.unpar.siamodels.Mahasiswa;

import java.util.List;


@InfoMataKuliah(nama = "Pengantar Sistem Informasi", sks = 3)
public class AIF182308 extends MataKuliah implements HasPrasyarat{

    @Override
    public boolean checkPrasyarat(Mahasiswa mahasiswa, List<String> reasonsContainer) {
        boolean ok = true;
        if (!mahasiswa.hasLulusKuliah("AIF181105")) {
            reasonsContainer.add("Tidak memenuhi prasyarat lulus AIF181105");
            ok = false;
        }
        if (!mahasiswa.hasTempuhKuliah("AIF182302")) {
            reasonsContainer.add("Tidak memenuhi prasyarat tempuh AIF182302");
            ok = false;
        }
        return ok;
    }
}

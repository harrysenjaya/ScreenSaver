package id.ac.unpar.siamodels.matakuliah;

import id.ac.unpar.siamodels.Mahasiswa;
import id.ac.unpar.siamodels.MataKuliah;
import id.ac.unpar.siamodels.InfoMataKuliah;
import id.ac.unpar.siamodels.matakuliah.interfaces.HasPraktikum;
import id.ac.unpar.siamodels.matakuliah.interfaces.HasPrasyarat;

import java.util.List;

/**
 * Kuliah ini merupakan kelanjutan dari kuliah Manajemen Informasi Basisdata.
 * Pada perkuliahan ini, mahasiswa akan mempelajari teknik-teknik pengelolaan
 * basis data dan membuat program dengan basis data yang optimal/efisien.
 *
 * @author Falahah . S.
 */
@InfoMataKuliah(nama = "Pemrograman Basis Data", sks = 2)
public class AIF314 extends MataKuliah implements HasPrasyarat, HasPraktikum {

    @Override
    public boolean checkPrasyarat(Mahasiswa mahasiswa, List<String> reasonsContainer) {
        if (!mahasiswa.hasTempuhKuliah("AIF204") && !mahasiswa.hasTempuhKuliah("AIF294")) {
            reasonsContainer.add("Tidak memenuhi prasyarat tempuh AIF204 atau AIF294");
            return false;
        }
        return true;
    }


}

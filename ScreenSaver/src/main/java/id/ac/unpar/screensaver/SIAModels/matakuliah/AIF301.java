package id.ac.unpar.siamodels.matakuliah;

import id.ac.unpar.siamodels.Mahasiswa;
import id.ac.unpar.siamodels.MataKuliah;
import id.ac.unpar.siamodels.InfoMataKuliah;
import id.ac.unpar.siamodels.matakuliah.interfaces.HasPrasyarat;

import java.util.List;

/**
 * Mata kuliah ini memperkenalkan kepada mahasiswa konsep dasar tentang *sistem
 * cerdas dan komponen-komponennya. " "Terdapat 4 topik utama yang dibahas yaitu
 * teknik pencarian untuk *penyelesaian masalah, representasi pengetahuan dalam
 * sistem *cerdas, pemodelan ketidakpastian dalam masalah dan teknik
 * pembelajaran mesin.
 *
 * @author Thomas Anung Basuki
 *
 */

@InfoMataKuliah(nama = "Pengantar Sistem Cerdas ", sks = 3)
public class AIF301 extends MataKuliah implements HasPrasyarat {

    @Override
    public boolean checkPrasyarat(Mahasiswa mahasiswa, List<String> reasonsContainer) {
        boolean ok = true;
        if (!mahasiswa.hasTempuhKuliah("AIF204") && !mahasiswa.hasTempuhKuliah("AIF294")) {
            reasonsContainer.add("Tidak memenuhi prasyarat tempuh AIF204 atau AIF294");
            ok = false;
        }
        return ok;
    }

}

package id.ac.unpar.siamodels.matakuliah;

import id.ac.unpar.siamodels.Mahasiswa;
import id.ac.unpar.siamodels.MataKuliah;
import id.ac.unpar.siamodels.InfoMataKuliah;
import id.ac.unpar.siamodels.matakuliah.interfaces.HasPraktikum;
import id.ac.unpar.siamodels.matakuliah.interfaces.HasPrasyarat;
import id.ac.unpar.siamodels.matakuliah.interfaces.HasResponsi;

import java.util.List;

/**
 * Mata kuliah ini memberikan kesempatan bagi mahasiswa untuk memperdalam konsep
 * tentang pengembangan sistem informasi dan mempraktekkan analisis kebutuhan,
 * analisis sistem dan perancangan sitem pada organisasi studi kasus;
 *
 * @author Veronica S. Moertini (moertini@unpar.ac.id)
 */

@InfoMataKuliah(nama = "Proyek Sistem Informasi 1", sks = 3)
public class AIF304 extends MataKuliah implements HasPrasyarat, HasPraktikum, HasResponsi {

    @Override
    public boolean checkPrasyarat(Mahasiswa mahasiswa, List<String> reasonsContainer) {
        if (!mahasiswa.hasTempuhKuliah("AIF303")) {
            reasonsContainer.add("Tidak memenuhi prasyarat tempuh AIF303");
            return false;
        }
        return true;
    }

}

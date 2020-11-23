package id.ac.unpar.siamodels.matakuliah;

import id.ac.unpar.siamodels.Mahasiswa;
import id.ac.unpar.siamodels.MataKuliah;
import id.ac.unpar.siamodels.InfoMataKuliah;
import id.ac.unpar.siamodels.matakuliah.interfaces.HasPrasyarat;

import java.util.List;

/**
 * Mempelajari Konsep Data, Informasi, Pengetahuan, Sistem Informasi, proses dan
 * pemodelan bisnis, jenis-jenis sistem informasi, untuk mendukung pengambilan
 * keputusan. Mempelajari trend Teknologi Informasi, tahap-tahap pembangunan
 * sistem informasi. Mempelajari pengantar : EIS, e-bisnis/e-commerce, Business
 * Intelligence, Cloud Computing dan Mobile Applications
 *
 * @author Rosa de Lima E. Padmowati (rosad5@unpar.ac.id)
 */

@InfoMataKuliah(nama = "Pengantar Sistem Informasi", sks = 3)
public class AIF303 extends MataKuliah implements HasPrasyarat {

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

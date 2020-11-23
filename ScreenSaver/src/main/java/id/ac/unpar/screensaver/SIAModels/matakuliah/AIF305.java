package id.ac.unpar.siamodels.matakuliah;

import id.ac.unpar.siamodels.Mahasiswa;
import id.ac.unpar.siamodels.MataKuliah;
import id.ac.unpar.siamodels.InfoMataKuliah;
import id.ac.unpar.siamodels.matakuliah.interfaces.HasPrasyarat;

import java.util.List;

/**
 * Mata kuliah ini memperkenalkan kepada mahasiswa konsep dasar jaringan
 * komputer dengan menggunakan top-down. Selain itu mengajarkan juga kepada
 * mahasiswa mengenai aplikasi-aplikasi berbasis jaringan sehingga diharapkan
 * mahasiswa dapat membuat aplikasi berbasis jaringan dengan menggunakan socket.
 * Pada akhirnya, mahasiswa akan ditugaskan untuk membangun jaringan komputer
 * LAN, baik menggunakan kabel maupun nirkabel.
 *
 * @author Chandra Wijaya (chandraw@unpar.ac.id)
 */

@InfoMataKuliah(nama = "Jaringan Komputer", sks = 4)
public class AIF305 extends MataKuliah implements HasPrasyarat {

    @Override
    public boolean checkPrasyarat(Mahasiswa mahasiswa, List<String> reasonsContainer) {
        boolean ok = true;
        if (!mahasiswa.hasTempuhKuliah("AIF206")) {
            reasonsContainer.add("Tidak memenuhi prasyarat tempuh AIF206");
            ok = false;
        }
        return ok;
    }

}

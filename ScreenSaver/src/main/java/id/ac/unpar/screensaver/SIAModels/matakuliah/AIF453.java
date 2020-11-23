package id.ac.unpar.siamodels.matakuliah;

import id.ac.unpar.siamodels.Mahasiswa;
import id.ac.unpar.siamodels.MataKuliah;
import id.ac.unpar.siamodels.InfoMataKuliah;
import id.ac.unpar.siamodels.matakuliah.interfaces.HasPrasyarat;
import java.util.List;

/**
 * Mata kuliah inimemperkenalkan kebutuhan organisasi terhadap sistem business
 * intelligent (BI) dan pemanfaatan BI untuk organisasi; memperkenalkan konsep
 * sistem business intelligent dan komponennya; Mempelajari tenik-teknik
 * analisis data bisnis dan visualisasi hasil analisis; Mempelajari konsep data
 * warehouse dan perancangannya dan fungsi OLAP; Mempraktekkan teknik-teknik
 * analisis data dan visualisasi hasil analisis.
 *
 * @author Veronica S. Moertini (moertini@unpar.ac.id)
 */

@InfoMataKuliah(nama = "Kecerdasan Bisnis", sks = 3)
public class AIF453 extends MataKuliah implements HasPrasyarat {

    @Override
    public boolean checkPrasyarat(Mahasiswa mahasiswa, List<String> reasonsContainer) {
        boolean ok = false;
        if (mahasiswa.hasTempuhKuliah("AIF204") || mahasiswa.hasTempuhKuliah("AIF294")) {
            ok = true;
        }
        if ((mahasiswa.hasTempuhKuliah("AIF102") || !mahasiswa.hasTempuhKuliah("AIF192")) && mahasiswa.calculateIPLulus() > 2.75) {
            ok = true;
        }
        if (!ok) {
            reasonsContainer.add("Tidak memenuhi prasyarat ((tempuh AIF204/294) atau (tempuh AIF102/AIF192 & IPK Lulus > 2.75))");
        }
        return ok;
    }

}

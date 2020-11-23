package id.ac.unpar.siamodels.matakuliah;

import id.ac.unpar.siamodels.Mahasiswa;
import id.ac.unpar.siamodels.MataKuliah;
import id.ac.unpar.siamodels.InfoMataKuliah;
import id.ac.unpar.siamodels.matakuliah.interfaces.HasPrasyarat;

import java.util.List;

/**
 * Mata kuliah ini: Memperkenalkan karakteristik dan teknik visualisasi dari
 * berbagai jenis data yang dapat dianalisis dengan teknik-teknik data mining;
 * mempelajari teknik-teknik penyiapan data untuk berbagai jenis data dan teknik
 * data mining; mempraktekkan teknik-teknik penyiapan data untuk menganalisis
 * data nyata/simulasi dengan memanfaatkan perangkat lunak aplikasi.
 *
 * @author Veronica S. Moertini (moertini@unpar.ac.id)
 */

@InfoMataKuliah(nama = "Penambangan Data", sks = 3)
public class AIF438 extends MataKuliah implements HasPrasyarat {

    @Override
    public boolean checkPrasyarat(Mahasiswa mahasiswa, List<String> reasonsContainer) {
        boolean ok = true;
        if (!mahasiswa.hasLulusKuliah("AIF102") && !mahasiswa.hasLulusKuliah("AIF192")) {
            reasonsContainer.add("Tidak memenuhi prasyarat lulus AIF102 atau AIF192");
            ok = false;
        }
        return ok;
    }

}

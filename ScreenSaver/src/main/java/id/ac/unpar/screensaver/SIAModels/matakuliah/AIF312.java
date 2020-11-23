package id.ac.unpar.siamodels.matakuliah;

import id.ac.unpar.siamodels.Mahasiswa;
import id.ac.unpar.siamodels.MataKuliah;
import id.ac.unpar.siamodels.InfoMataKuliah;
import id.ac.unpar.siamodels.matakuliah.interfaces.HasPraktikum;
import id.ac.unpar.siamodels.matakuliah.interfaces.HasPrasyarat;

import java.util.List;

/**
 * Mata kuliah ini memberikan pengetahuan awal tentang keamanan informasi. Pada
 * beberapa pertemuan awal, dibahas keamanan informasi secara matematis, yaitu
 * di materi-materi seputar kriptografi dan serangannya. Lalu, dibahas pula
 * konsep keamanan informasi pada jaringan komputer dan pada software.
 *
 * @author Mariskha Tri Adithia (mariskha@unpar.ac.id)
 */

@InfoMataKuliah(nama = "Keamanan Informasi", sks = 2)
public class AIF312 extends MataKuliah implements HasPrasyarat, HasPraktikum {

    @Override
    public boolean checkPrasyarat(Mahasiswa mahasiswa, List<String> reasonsContainer) {
        if (!mahasiswa.hasTempuhKuliah("AIF305")) {
            reasonsContainer.add("Tidak memenuhi prasyarat tempuh AIF305");
            return false;
        }
        return true;
    }

}

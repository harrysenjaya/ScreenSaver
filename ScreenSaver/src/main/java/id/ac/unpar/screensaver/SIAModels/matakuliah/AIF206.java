package id.ac.unpar.siamodels.matakuliah;

import id.ac.unpar.siamodels.Mahasiswa;
import id.ac.unpar.siamodels.MataKuliah;
import id.ac.unpar.siamodels.InfoMataKuliah;
import id.ac.unpar.siamodels.matakuliah.interfaces.HasPrasyarat;

import java.util.List;

/**
 * Mata kuliah ini memperkenalkan kepada mahasiswa mengenai konsep sistem 
 * operasi, jenis-jenis sistem operasi yang digunakan dalam kehidupan 
 * sehari-hari dan beberapa perangkat keras yang dibutuhkan pada komputer. 
 * Selain itu juga mempelajari mengenai teknik dan algoritma yang digunakan 
 * dalam pengelolaan sistem operasi.
 * @author Chandra Wijaya (chandraw@unpar.ac.id)
 */
@InfoMataKuliah(nama = "Sistem Operasi", sks = 4)
public class AIF206 extends MataKuliah implements HasPrasyarat{

    @Override
    public boolean checkPrasyarat(Mahasiswa mahasiswa, List<String> reasonsContainer) {
        boolean ok = true;
        if (!mahasiswa.hasTempuhKuliah("AIF102") && !mahasiswa.hasTempuhKuliah("AIF192")) {
            reasonsContainer.add("Tidak memenuhi prasyarat tempuh AIF102 atau AIF192");
            ok = false;
        }
        if (!mahasiswa.hasTempuhKuliah("AIF205")) {
            reasonsContainer.add("Tidak memenuhi prasyarat tempuh AIF205");
            ok = false;
        }
        return ok;
    }

}

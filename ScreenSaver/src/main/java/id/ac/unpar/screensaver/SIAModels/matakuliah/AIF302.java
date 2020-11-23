package id.ac.unpar.siamodels.matakuliah;

import id.ac.unpar.siamodels.Mahasiswa;
import id.ac.unpar.siamodels.MataKuliah;
import id.ac.unpar.siamodels.InfoMataKuliah;
import id.ac.unpar.siamodels.matakuliah.interfaces.HasPrasyarat;

import java.util.List;

/**
 * Mata kuliah ini melatih mahasiswa dalam menulis ilmiah serta memperkenalkan
 * metodologi penelitian serta kakas untuk menulis ilmiah.
 *
 * @author Thomas Anung Basuki (anung@unpar.ac.id)
 */

@InfoMataKuliah(nama = "Penulisan Ilmiah", sks = 2)
public class AIF302 extends MataKuliah implements HasPrasyarat {

    @Override
    public boolean checkPrasyarat(Mahasiswa mahasiswa, List<String> reasonsContainer) {
        int sksLulus = mahasiswa.calculateSKSLulus();
        if (sksLulus < 84) {
            reasonsContainer.add("SKS Lulus " + sksLulus + ", belum mencapai syarat minimal 84");
            return false;
        }
        return true;
    }

}

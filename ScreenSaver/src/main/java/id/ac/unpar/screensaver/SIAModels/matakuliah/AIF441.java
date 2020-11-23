package id.ac.unpar.siamodels.matakuliah;

import java.util.List;

import id.ac.unpar.siamodels.InfoMataKuliah;
import id.ac.unpar.siamodels.Mahasiswa;
import id.ac.unpar.siamodels.MataKuliah;
import id.ac.unpar.siamodels.matakuliah.interfaces.HasPraktikum;
import id.ac.unpar.siamodels.matakuliah.interfaces.HasPrasyarat;

/**
 * Mata kuliah ini memperkenalkan kepada mahasiswa konsep jaringan lanjut
 * terutama di layer data link dan layer network. Materi utama dari mata kuliah
 * ini adalah pengembangan jaringan dan pengenalan fungsi-fungsi yang terdapat
 * pada alat jaringan Cisco yang berkaitan dengan layer 2 dan layer 3.
 *
 * @author Chandra Wijaya, ST., MT. (chandraw@unpar.ac.id)
 */

@InfoMataKuliah(nama = "Administrasi Jaringan Komputer 3", sks = 3)
public class AIF441 extends MataKuliah implements HasPraktikum, HasPrasyarat {

	@Override
	public boolean checkPrasyarat(Mahasiswa mahasiswa, List<String> reasonsContainer) {
		if (!mahasiswa.hasLulusKuliah("AIF342")) {
			reasonsContainer.add("Tidak memenuhi prasyarat lulus AIF342");			
			return false;
		}
		return true;
	}
	
}

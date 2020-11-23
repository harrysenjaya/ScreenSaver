package id.ac.unpar.siamodels.matakuliah;

import id.ac.unpar.siamodels.Mahasiswa;
import id.ac.unpar.siamodels.MataKuliah;
import id.ac.unpar.siamodels.InfoMataKuliah;
import id.ac.unpar.siamodels.matakuliah.interfaces.HasPrasyarat;

import java.util.List;

/**
 * Mata kuliah ini mengajarkan kepada mahasiswa teknik-teknik untuk membuat 
 * layanan berbasis web. Mahasiswa diperkenalkan dengan standar-standar seperti 
 * HTTP, XML, JSON dan diajarkan untuk memanfaatkannya dalam membuat maupun 
 * menggunakan layanan pihak ketiga. Dalam kuliah ini, juga akan diperkenalkan 
 * minimal satu layanan pihak ketiga yang dapat dimanfaatkan mahasiswa, seperti 
 * Google Places Web Service.
 * @author Pascal (pascal@unpar.ac.id)
 */
@InfoMataKuliah(nama = "Layanan Berbasis Web", sks = 3)
public class AIF469 extends MataKuliah implements HasPrasyarat {

	@Override
	public boolean checkPrasyarat(Mahasiswa mahasiswa, List<String> reasonsContainer) {
		boolean ok = true;
		if (!mahasiswa.hasTempuhKuliah("AIF305")) {
			reasonsContainer.add("Tidak memenuhi prasyarat tempuh AIF305");
			ok = false;
		}
		if (!mahasiswa.hasTempuhKuliah("AIF315")) {
			reasonsContainer.add("Tidak memenuhi prasyarat tempuh AIF315");			
			ok = false;
		}
		return ok;
	}
        
}

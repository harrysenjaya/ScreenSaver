package id.ac.unpar.siamodels.matakuliah;

import id.ac.unpar.siamodels.Mahasiswa;
import id.ac.unpar.siamodels.MataKuliah;
import id.ac.unpar.siamodels.InfoMataKuliah;
import id.ac.unpar.siamodels.matakuliah.interfaces.HasPraktikum;
import id.ac.unpar.siamodels.matakuliah.interfaces.HasPrasyarat;

import java.util.List;
/**
 * Mata kuliah ini memperkenalkan konsep perangkat mobile dan pemrograman pada perangkat 
 * mobile. Pemrograman dikhususkan pada lingkungan J2ME dan Android.
 * Untuk meningkatkan keterampilan pemrograman dilengkapi dengan praktikum. 
 * Sedangkan untuk mendapatkan pengalaman penerapan konsep diberikan tugas implementasi suatu 
 * kasus pada lingkungan mobile-cloud dengan kasus yang sudah ditentukan.
 * 
 * @author Gede Karya (gkarya@unpar.ac.id)
 */
@InfoMataKuliah(nama = "Pemrograman Aplikasi Bergerak", sks = 2)
public class AIF318 extends MataKuliah implements HasPrasyarat, HasPraktikum {

	@Override
	public boolean checkPrasyarat(Mahasiswa mahasiswa, List<String> reasonsContainer) {
		boolean ok = true;
		if (!mahasiswa.hasTempuhKuliah("AIF102") && !mahasiswa.hasTempuhKuliah("AIF192")) {
			reasonsContainer.add("Tidak memenuhi prasyarat tempuh AIF102 atau AIF192");
			ok = false;
		}
		if (!mahasiswa.hasTempuhKuliah("AIF201")) {
			reasonsContainer.add("Tidak memenuhi prasyarat tempuh AIF201");			
			ok = false;
		}
		return ok;
	}
}

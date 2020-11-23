package id.ac.unpar.siamodels.matakuliah;

import id.ac.unpar.siamodels.Mahasiswa;
import id.ac.unpar.siamodels.MataKuliah;
import id.ac.unpar.siamodels.InfoMataKuliah;
import id.ac.unpar.siamodels.matakuliah.interfaces.HasPraktikum;
import id.ac.unpar.siamodels.matakuliah.interfaces.HasPrasyarat;

import java.util.List;
/**
 * Mata kuliah ini memperkenalkan konsep-konsep dasar komputasi paralel, dimana sebuah 
 * program yang berjalan secara paralel harus memiliki safety property dan liveness property. 
 * Mahasiswa dikenalkan dengan beberapa teknik pemrograman multi-thread
 * seperti lock, monitor, barrier, thread pool, dan sebagainya, yang diimplementasikan 
 * dalam bahasa pemrograman Java. Mahasiswa juga dikenalkan dengan beberapa metode untuk 
 * menganalisis kebenaran program baik secara matematis maupun secara praktis dengan bantuan 
 * model checker.
 * @author Joanna Helga, M.Sc. (joanna@unpar.ac.id)
 */
@InfoMataKuliah(nama = "Komputasi Paralel", sks = 2)
public class AIF316 extends MataKuliah implements HasPrasyarat, HasPraktikum {

	@Override
	public boolean checkPrasyarat(Mahasiswa mahasiswa, List<String> reasonsContainer) {
		if (!mahasiswa.hasTempuhKuliah("AIF102") && !mahasiswa.hasTempuhKuliah("AIF192")) {
			reasonsContainer.add("Tidak memenuhi prasyarat tempuh AIF102 atau AIF192");
			return false;
		}
		return true;
	}
}

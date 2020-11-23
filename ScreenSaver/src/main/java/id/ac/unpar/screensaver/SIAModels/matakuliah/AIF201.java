package id.ac.unpar.siamodels.matakuliah;

import id.ac.unpar.siamodels.matakuliah.interfaces.HasPrasyarat;
import id.ac.unpar.siamodels.Mahasiswa;
import id.ac.unpar.siamodels.MataKuliah;
import id.ac.unpar.siamodels.InfoMataKuliah;
import id.ac.unpar.siamodels.matakuliah.interfaces.HasPraktikum;
import id.ac.unpar.siamodels.matakuliah.interfaces.HasResponsi;

import java.util.List;

/**
 * Mata kuliah ini memperkenalkan prinsip-prinsip yang digunakan dalam 
 * melakukan analisa serta desain prorgram berorientasi objek. Di samping itu, 
 * mahasiswa juga belajar menggunakan kakas berupa diagram UML (Unified 
 * Modelling Language) sehingga dapat mengkomunikasikan desain secara visual. 
 * Mahasiswa juga akan mengenal beberapa software design pattern dari Gang of 
 * Four. Terakhir, mahasiswa akan belajar mengenai konsep MVC 
 * (Model-View-Controller) yang menjadi dasar dari banyak framework masa kini.
 * Bahasa yang digunakan adalah bahasa Java, namun diusahakan tetap umum 
 * sehingga dapat diaplikasikan pada bahasa yang lain.
 * @author Pascal (pascal@unpar.ac.id)
 */
@InfoMataKuliah(nama = "Analisis & Desain Berorientasi Objek", sks = 4)
public class AIF201 extends MataKuliah implements HasPrasyarat, HasPraktikum, HasResponsi {

	@Override
	public boolean checkPrasyarat(Mahasiswa mahasiswa, List<String> reasonsContainer) {
		boolean ok = true;
		if (!mahasiswa.hasLulusKuliah("AIF101") && !mahasiswa.hasLulusKuliah("AIF191")) {
			reasonsContainer.add("Tidak memenuhi prasyarat lulus AIF101 atau AIF191");
			ok = false;
		}
		return ok;
	}

}

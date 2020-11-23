package id.ac.unpar.siamodels.matakuliah;

import id.ac.unpar.siamodels.matakuliah.interfaces.HasPrasyarat;
import id.ac.unpar.siamodels.Mahasiswa;
import id.ac.unpar.siamodels.MataKuliah;
import id.ac.unpar.siamodels.InfoMataKuliah;
import id.ac.unpar.siamodels.matakuliah.interfaces.HasPraktikum;
import id.ac.unpar.siamodels.matakuliah.interfaces.HasResponsi;


import java.util.List;

/**
 * Mata kuliah ini memperkenalkan kepada mahasiswa beberapa algoritma dan 
 * struktur data, alternatif cara implementasinya, dan analisis kompleksitas 
 * waktunya. Mahasiswa diberikan beberapa masalah komputasi yang harus 
 * diselesaikan dengan menggunakan algoritma atau struktur data yang sudah 
 * diperkenalkan dan mengimplementasikannya dalam bahasa pemrograman Java.
 * @author Joanna Helga, M.Sc. (joanna@unpar.ac.id)
 */
@InfoMataKuliah(nama = "Desain & Analisis Algoritma", sks = 4)
public class AIF202 extends MataKuliah implements HasPrasyarat, HasResponsi, HasPraktikum {

	@Override
	public boolean checkPrasyarat(Mahasiswa mahasiswa, List<String> reasonsContainer) {
		boolean ok = true;
		if (!mahasiswa.hasLulusKuliah("AIF102") && !mahasiswa.hasLulusKuliah("AIF192")) {
			reasonsContainer.add("Tidak memenuhi prasyarat lulus AIF102 atau AIF192");
			ok = false;
		}
		int angkatan = mahasiswa.getTahunAngkatan();
		if (angkatan >= 2012 && angkatan <= 2014) {
			if (!mahasiswa.hasTempuhKuliah("AIF203")) {
				reasonsContainer.add("Angkatan " + angkatan + " tetapi tidak memenuhi prasyarat tempuh AIF103");			
				ok = false;
			}
		}
		return ok;
	}

}

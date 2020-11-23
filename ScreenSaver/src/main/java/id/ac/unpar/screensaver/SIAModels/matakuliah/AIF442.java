package id.ac.unpar.siamodels.matakuliah;

import java.util.List;

import id.ac.unpar.siamodels.InfoMataKuliah;
import id.ac.unpar.siamodels.Mahasiswa;
import id.ac.unpar.siamodels.MataKuliah;
import id.ac.unpar.siamodels.matakuliah.interfaces.HasPraktikum;
import id.ac.unpar.siamodels.matakuliah.interfaces.HasPrasyarat;

@InfoMataKuliah(nama = "Administrasi Jaringan Komputer 4", sks = 3)
public class AIF442 extends MataKuliah implements HasPraktikum, HasPrasyarat {

	@Override
	public boolean checkPrasyarat(Mahasiswa mahasiswa, List<String> reasonsContainer) {
		if (!mahasiswa.hasLulusKuliah("AIF441")) {
			reasonsContainer.add("Tidak memenuhi prasyarat lulus AIF441");			
			return false;
		}
		return true;
	}
	
}

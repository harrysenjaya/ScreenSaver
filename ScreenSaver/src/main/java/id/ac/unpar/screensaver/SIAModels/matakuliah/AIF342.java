package id.ac.unpar.siamodels.matakuliah;

import java.util.List;

import id.ac.unpar.siamodels.Mahasiswa;
import id.ac.unpar.siamodels.MataKuliah;
import id.ac.unpar.siamodels.InfoMataKuliah;
import id.ac.unpar.siamodels.matakuliah.interfaces.HasPraktikum;
import id.ac.unpar.siamodels.matakuliah.interfaces.HasPrasyarat;

@InfoMataKuliah(nama = "Administrasi Jaringan Komputer 2", sks = 3)
public class AIF342 extends MataKuliah implements HasPraktikum, HasPrasyarat {

	@Override
	public boolean checkPrasyarat(Mahasiswa mahasiswa, List<String> reasonsContainer) {
		if (!mahasiswa.hasLulusKuliah("AIF341")) {
			reasonsContainer.add("Tidak memenuhi prasyarat lulus AIF341");			
			return false;
		}
		return true;
	}

}

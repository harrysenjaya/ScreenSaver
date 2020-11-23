package id.ac.unpar.siamodels.matakuliah;

import id.ac.unpar.siamodels.Mahasiswa;
import id.ac.unpar.siamodels.MataKuliah;
import id.ac.unpar.siamodels.InfoMataKuliah;
import id.ac.unpar.siamodels.matakuliah.interfaces.HasPrasyarat;

import java.util.List;

@InfoMataKuliah(nama = "Etika Profesi", sks = 2)
public class APS402 extends MataKuliah implements HasPrasyarat {

	@Override
	public boolean checkPrasyarat(Mahasiswa mahasiswa, List<String> reasonsContainer) {
		int sksLulus = mahasiswa.calculateSKSLulus();
		if (sksLulus < 90) {
			reasonsContainer.add("SKS Lulus " + sksLulus + ", belum mencapai syarat minimal 90");			
			return false;
		}
		return true;
	}

}

package id.ac.unpar.siamodels.matakuliah;

import id.ac.unpar.siamodels.Mahasiswa;
import id.ac.unpar.siamodels.MataKuliah;
import id.ac.unpar.siamodels.InfoMataKuliah;
import id.ac.unpar.siamodels.matakuliah.interfaces.HasPrasyarat;

import java.util.List;

/**
 * Mata kuliah ini memperkenalkan kepada mahasiswa konsep struktur diskret yang 
 * digunakan pada bidang informatika diantaranya graph, pohon dan finite state 
 * machine
 * @author Dr.rer.nat Cecilia Esti Nugraheni (cheni@unpar.ac.id)
 */
@InfoMataKuliah(nama = "Struktur Diskrit", sks = 4)
public class AIF203 extends MataKuliah implements HasPrasyarat {

	@Override
	public boolean checkPrasyarat(Mahasiswa mahasiswa, List<String> reasonsContainer) {
		boolean ok = true;
		if (!mahasiswa.hasTempuhKuliah("AIF103")) {
			reasonsContainer.add("Tidak memenuhi prasyarat tempuh AIF103");			
			ok = false;
		}
		return ok;
	}

}

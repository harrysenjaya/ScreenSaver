package id.ac.unpar.siamodels.matakuliah;

import java.util.List;

import id.ac.unpar.siamodels.Mahasiswa;
import id.ac.unpar.siamodels.MataKuliah;
import id.ac.unpar.siamodels.InfoMataKuliah;
import id.ac.unpar.siamodels.matakuliah.interfaces.HasPrasyarat;
/**
 * 1. Memberikan wawasan kepada mahasiswa tentang kemunculan dan pemanfaatan teknologi baru, 
 * khususnya yang berkaitan dengan komputer, dan dampaknya terhadap masyarakat luas.
 * 2. Memberikan kesadaran dan panduan bersikap kepada mahasiswa dalam menghadapi gejolak yang
 * disebabkan oleh munculnya teknologi baru, khususnya yang berkaitan dengan komputer.
 * 
 * @author Oerip S. Santosa (oerip@unpar.ac.id)
 */
@InfoMataKuliah(nama = "Komputer & Masyarakat", sks = 2)
public class AIF403 extends MataKuliah implements HasPrasyarat {

      	@Override
      	public boolean checkPrasyarat(Mahasiswa mahasiswa, List<String> reasonsContainer) {
      		int sksLulus = mahasiswa.calculateSKSLulus();
      		if (sksLulus < 70) {
      			reasonsContainer.add("SKS Lulus " + sksLulus + ", belum mencapai syarat minimal 70");			
      			return false;
      		}
      		return true;
      	}
}

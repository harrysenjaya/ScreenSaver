package id.ac.unpar.siamodels.matakuliah;

import id.ac.unpar.siamodels.Mahasiswa;
import id.ac.unpar.siamodels.MataKuliah;
import id.ac.unpar.siamodels.InfoMataKuliah;
import id.ac.unpar.siamodels.matakuliah.interfaces.HasPrasyarat;

import java.util.List;

/**
 * Mata kuliah ini memperkenalkan konsep kewirausahaan dengan memanfaatkan teknologi, khususnya
 * teknologi informasi, sebagai basis usaha dan inovasi produk/jasa; Mempelajari
 * teknik mencari peluang dan merumuskan bidang usaha spesifik yang akan
 * diterjuni; Mempelajari konsep manajemen pemasaran, keuangan dan SDM dalam
 * kaitannya dengan berwira-usaha di bidang TI; Menyusun proposal bisnis untuk
 * berwira-usaha di bidang TI dan mempresentasikannya.
 * 
 * @author Veronica S. Moertini (moertini@unpar.ac.id) 
 */
@InfoMataKuliah(nama = "Kewirausahaan Berbasis Teknologi", sks = 3)
public class AIF457 extends MataKuliah implements HasPrasyarat {

    @Override
    public boolean checkPrasyarat(Mahasiswa mahasiswa, List<String> reasonsContainer) {
        boolean ok = true;
        int sksLulus = mahasiswa.calculateSKSLulus();
        if (sksLulus < 70) {
            reasonsContainer.add("SKS Lulus " + sksLulus + ", belum mencapai syarat minimal 70");
            return false;
        }
        return ok;
    }

}

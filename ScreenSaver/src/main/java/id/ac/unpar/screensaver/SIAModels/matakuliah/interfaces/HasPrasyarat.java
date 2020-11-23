package id.ac.unpar.siamodels.matakuliah.interfaces;

import java.util.List;

import id.ac.unpar.siamodels.Mahasiswa;

/**
 * Mendefinisikan kelas-kelas yang memiliki prasyarat, terkustomisasi
 * untuk seorang {@link Mahasiswa}. Jika ada tambahan, jangan lupa untuk
 * mendaftarkannya di {@link #DEFAULT_HASPRASYARAT_CLASSES}. Jika berubah package,
 * jangan lupa mengupdate {@link #DEFAULT_PRASYARAT_PACKAGE}.
 * @author pascal
 *
 */
public interface HasPrasyarat {
	
	/**
	 * Daftar dari nama kelas default seluruh turunan interface ini. Perlu didaftarkan
	 * manual, karena Java reflection tidak dapat mendeteksi otomatis.
	 */
	public String[] DEFAULT_HASPRASYARAT_CLASSES = {
			"AIF181100", "AIF182101", "AIF182103", "AIF182105",
			"AIF182100", "AIF182302", "AIF182204", "AIF182106",
			"AIF182308", "AIF183201", "AIF183303", "AIF183305",
			"AIF183107", "AIF183209", "AIF183111", "AIF183300",
			"AIF183204", "AIF183106", "AIF184303", "AIF184001",
			"AIF184002", "AIF184004", "AIF182111", "AIF182112",
			"AIF183117", "AIF183119", "AIF182121", "AIF183227",
			"AIF183331", "AIF183333", "AIF183339", "AIF183141",
			"AIF183143", "AIF183145", "AIF183147", "AIF183149",
			"AIF183153", "AIF183155", "AIF183112", "AIF183114",
			"AIF183116", "AIF183118", "AIF183120", "AIF183122",
			"AIF183124", "AIF183232", "AIF183236", "AIF183340",
			"AIF183342", "AIF183348", "AIF183250", "AIF184109",
			"AIF184115", "AIF184119", "AIF184121", "AIF184123",
			"AIF184125", "AIF184129", "AIF184231", "AIF184233",
			"AIF184235", "AIF184339", "AIF184341", "AIF184345",
			"AIF184247", "AIF184104", "AIF184106", "AIF184108",
			"AIF184110", "AIF184114", "AIF184116", "AIF184222",
			"AIF184224", "AIF184228", "AIF184230", "AIF184334",
			"AIF184338", "AIF184340", "AIF184344"};
	
	/**
	 * Package tempat menyimpan seluruh turunan standar interface ini. Perlu didefinisikan
	 * manual, karena Java reflection tidak dapat mendeteksi otomatis.
	 */
	public String DEFAULT_PRASYARAT_PACKAGE = "id.ac.unpar.siamodels.matakuliah";
	
	/**
	 * Memeriksa prasyarat-prasyarat dari kuliah, spesifik untuk mahasiswa
	 * yang dituju. Jika ada pesan-pesan khusus, akan ditambahkan pada parameter
	 * reasonsContainer.
	 * @param mahasiswa prasyarat kuliah akan diperiksa spesifik pada mahasiswa ini
	 * @param reasonsContainer pesan-pesan terkait prasyarat akan ditambahkan di sini, jika ada. 
	 * @return true jika seluruh prasyarat dipenuhi, false jika tidak. 
	 */
	public boolean checkPrasyarat(Mahasiswa mahasiswa, List<String> reasonsContainer);
}

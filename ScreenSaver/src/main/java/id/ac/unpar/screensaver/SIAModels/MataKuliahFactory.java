package id.ac.unpar.siamodels;

import java.lang.annotation.Annotation;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Logger;

/**
 * Kelas yang bertugas membuat kelas mata kuliah, dan menyimpannya untuk bisa
 * digunakan kemudian (untuk hemat memori).
 * 
 * @author pascal
 *
 */
public class MataKuliahFactory {

	/**
	 * Lokasi package untuk daftar mata kuliah
	 */
	public static String DEFAULT_MATAKULIAH_PACKAGE = "id.ac.unpar.siamodels.matakuliah.kurikulum2018";

	/**
	 * Singleton instance to factory.
	 */
	private static MataKuliahFactory instance = null;

	/**
	 * Singleton instances untuk mata kuliah.
	 */
	private final SortedMap<String, MataKuliah> mataKuliahCache;

	public static MataKuliahFactory getInstance() {
		if (instance == null) {
			instance = new MataKuliahFactory();
		}
		return instance;
	}

	protected MataKuliahFactory() {
		this.mataKuliahCache = new TreeMap<>();
	}

	/**
	 * Membuat baru atau mendapatkan mata kuliah, jika memiliki informasi
	 * nama dan jumlah SKS.
	 * 
	 * @param kode
	 *            kode mata kuliah
	 * @param sks
	 *            jumlah SKS
	 * @param nama
	 *            nama mata kuliah
	 * @return objek mata kuliah
	 */
	public MataKuliah createMataKuliah(String kode, int sks, String nama) {
		MataKuliah mk = this.mataKuliahCache.get(kode);
		// Coba dapatkan mata kuliah dari cache
		if (mk != null) {
			// Update jika kita punya info lebih baik
			if (mk.getSks() == null || mk.getNama() == null) {
				mk = new MataKuliah(kode, nama, sks) {};
				this.mataKuliahCache.put(kode, mk);
			}
			return mk;
		}

		// Coba dapatkan dari kelas statik
		Class<?> mkClass;
		try {
			mkClass = Class.forName(DEFAULT_MATAKULIAH_PACKAGE + "." + kode);
			mk = (MataKuliah) mkClass.newInstance();
		} catch (ClassNotFoundException e) {
			mk = new MataKuliah(kode, nama, sks) {};
			Logger.getGlobal().warning("Class is not listed: " + String.format("%s-%d %s", kode, sks, nama));
		} catch (InstantiationException e) {
			Logger.getGlobal().warning("Internal error: " + e.getMessage());
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			Logger.getGlobal().warning("Internal error: " + e.getMessage());
			e.printStackTrace();
		}
		this.mataKuliahCache.put(kode, mk);
		return mk;
	}
	
	/**
	 * Membuat baru atau mendapatkan mata kuliah, jika tidak memiliki informasi
	 * nama dan jumlah SKS.
	 * 
	 * @param kode
	 *            kode mata kuliah
	 * @return objek mata kuliah
	 * @throws IllegalStateException
	 *             jika sks dan tidak sesuai dengan yang ada di kode
	 */
	public MataKuliah createMataKuliah(final String kode)
			throws IllegalStateException {
		MataKuliah mk = this.mataKuliahCache.get(kode);
		// Coba dapatkan mata kuliah dari cache
		if (mk != null) {
			return mk;
		}

		// Coba dapatkan dari kelas statik
		Class<?> mkClass;
		try {
			mkClass = Class.forName(DEFAULT_MATAKULIAH_PACKAGE + "." + kode);
			mk = (MataKuliah) mkClass.newInstance();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("Mata kuliah " + kode + " tidak ditemukan!");
		} catch (InstantiationException e) {
			Logger.getGlobal().warning("Internal error: " + e.getMessage());
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			Logger.getGlobal().warning("Internal error: " + e.getMessage());
			e.printStackTrace();
		}
		this.mataKuliahCache.put(kode, mk);
		return mk;
	}

}

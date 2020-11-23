package id.ac.unpar.siamodels;

import java.io.Serializable;

public abstract class MataKuliah implements Serializable {
	private final String kode;
	private final String nama;
	private final Integer sks;
	
	public MataKuliah() {
		this.kode = this.getClass().getSimpleName();
		if (this.getClass().isAnnotationPresent(InfoMataKuliah.class)) {
			InfoMataKuliah infoMK = (InfoMataKuliah) this.getClass().getAnnotation(InfoMataKuliah.class);
			this.nama = infoMK.nama();
			this.sks = infoMK.sks();
		} else {
			this.nama = null;
			this.sks = null;
		}
	}
	
	public MataKuliah(String kode, String nama, int sks) {
		this.kode = kode;
		this.nama = nama;
		this.sks = sks;
	}

	public String getKode() {
		return kode;
	}

	public String getNama() {
		return nama;
	}

	public Integer getSks() {
		return sks;
	}

	@Override
	public String toString() {
		return "{" + "kode=" + kode + ", nama=" + nama + ", sks=" + sks + '}';
	}
}

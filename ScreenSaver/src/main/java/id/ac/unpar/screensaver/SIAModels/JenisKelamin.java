package id.ac.unpar.siamodels;

public enum JenisKelamin {
	LAKI_LAKI("Laki-laki"), PEREMPUAN("Perempuan");
	private final String siakadCode;

	JenisKelamin(String siakadCode) {
		this.siakadCode = siakadCode;
	}

	/**
	 * Mendapatkan kode pada SIAKAD
	 *
	 * @return kode SIAKAD
	 */
	public String getSIAKADCode() {
		return this.siakadCode;
	}

	/**
	 * Mendapatkan enum yang sesuai dari kode SIAKAD nya
	 *
	 * @param siakadCode kode SIAKAD
	 * @return enum yang sesuai
	 */
	public static JenisKelamin fromSIAKADCode(String siakadCode) {
		for (JenisKelamin jenisKelamin : JenisKelamin.values()) {
			if (jenisKelamin.getSIAKADCode().equals(siakadCode)) {
				return jenisKelamin;
			}
		}
		return null;
	}
}

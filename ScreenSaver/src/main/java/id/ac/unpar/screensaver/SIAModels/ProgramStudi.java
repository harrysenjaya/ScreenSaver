package id.ac.unpar.siamodels;

/**
 * Representasi program studi. Untuk saat ini menggunakan enum karena sederhana.
 *
 * @author pascal
 */
public enum ProgramStudi {
	MATEMATIKA("616"),
	FISIKA("617"),
	INFORMATIKA("618");
	private final String siakadCode;

	ProgramStudi(String siakadCode) {
		this.siakadCode = siakadCode;
	}

	/**
	 * Mendapatkan kode pada SIAKAD
	 * @return kode SIAKAD
	 */
	public String getSIAKADCode() {
		return this.siakadCode;
	}

	/**
	 * Mencari enum yang sesuai berdasarkan kode SIAKAD nya
	 * @param siakadCode kode siakad
	 * @return enum yang sesuai
	 */
	public static ProgramStudi fromSIAKADCode(String siakadCode) {
		for (ProgramStudi status : ProgramStudi.values()) {
			if (status.getSIAKADCode().equals(siakadCode)) {
				return status;
			}
		}
		return null;
	}

}

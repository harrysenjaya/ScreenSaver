package id.ac.unpar.screensaver.siakad;

import id.ac.unpar.siamodels.JenisKelamin;
import id.ac.unpar.siamodels.Mahasiswa;
import id.ac.unpar.siamodels.MataKuliahFactory;
import id.ac.unpar.siamodels.ProgramStudi;
import id.ac.unpar.siamodels.Semester;
import id.ac.unpar.siamodels.TahunSemester;
import java.io.IOException;
import java.net.ProtocolException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author pascal
 */
public class SIAkad {

	protected static final String SIAKAD_BASE_URL = "https://siakad.unpar.ac.id";
	protected static final String SSO_BASE_URL = "https://sso.unpar.ac.id";
	protected static final String MU_BASE_URL = "https://mu.unpar.ac.id";
	protected static final int DEFAULT_TIMEOUT = 60000;

	protected final Logger logger = Logger.getLogger("id.ac.unpar.siakadgateway");

	// Implementation choice: I choose to hardcode the names here, because
	// it will be more flexible in case SIAkad uses different names compared
	// to Java/OS's standard.
	public static final String[] MONTH_NAMES = {
		"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"
	};

    Token token = null;

	public SIAkad() {
		this(false);
	}

	public SIAkad(boolean verbose) {
		if (verbose) {
			for (Handler handler : logger.getHandlers()) {
				handler.setLevel(Level.WARNING);
			}
		}
	}

	/**
	 * Melakukan login ke SI Akademik. Semua transaksi lain harus diawali
	 * dengan login.
	 *
	 * @param username username dosen (...@unpar.ac.id)
	 * @param password password password CAS
	 * @return true jika login berhasil, false jika username/password salah.
	 * @throws IOException jika terjadi kesalahan komunikasi
	 */
	public boolean login(String username, String password) throws IOException {
		try {
			Map<String, String> cookies;
			// 0. Trigger SIAkad website
			Connection connection = createBaseConnection(SIAKAD_BASE_URL, null);
			connection.timeout(DEFAULT_TIMEOUT);
			Response response = connection.execute();
			cookies = response.cookies();
			logConnnection(connection);
			// Should get a CAS response

			// 1. Simulate user lookup
			Connection ajaxConnection = Jsoup.connect(MU_BASE_URL + "/api/users/lookup");
			ajaxConnection.data("username", username);
			ajaxConnection.cookies(cookies);
			ajaxConnection.method(Connection.Method.POST);
			ajaxConnection.ignoreContentType(true); // JSON is expected
			Response ajaxResponse = ajaxConnection.execute();
			cookies = ajaxResponse.cookies();
			logConnnection(ajaxConnection);
			// We skip JSON parsing for now

			// 2. Perform login at SSO
			Document document = response.parse();
			String execution = document.select("input[name=execution]").val();
			String jsessionid = response.cookie("JSESSIONID");
			connection = Jsoup.connect(SSO_BASE_URL + "/login");
			connection.cookies(cookies);
			connection.data("username", username);
			connection.data("password", password);
			connection.data("execution", execution);
			connection.data("_eventId", "submit");
			connection.data("geolocation", "");
			connection.data("submit", "Login3");
			connection.method(Connection.Method.POST);
			response = connection.execute();
			logConnnection(connection);
			token = new Token(response.cookies());
			return true;
		} catch (HttpStatusException hse) {
			if (hse.getStatusCode() / 100 == 5) {
				throw new IOException("Protocol error: " + hse.getStatusCode() + " - " + hse.getUrl());
			} else {
				return false;
			}
		}
	}

	/**
	 * Perform logout
	 *
	 * @throws IOException when there is communication error.
	 */
	public void logout() throws IOException {
		Connection connection = createBaseConnection(SIAKAD_BASE_URL + "/logout", null);
		connection.method(Method.GET);
		connection.execute();
		logConnnection(connection);
		token = null;
	}

	/**
	 * Mendapatkan daftar mahasiswa yang diwalikan oleh dosen terlogin
	 * (backward compatible tanpa parameter: seluruh program studi (yang
	 * terdaftar), status aktif, dan seluruh angkatan
	 *
	 * @return mahasiswa yang diwalikan
	 * @throws IllegalStateException jika belum login
	 * @throws IOException kesalahan komunikasi
	 */
	public List<Mahasiswa> requestMahasiswaList() throws IllegalStateException, IOException {
		return requestMahasiswaList(null, null, null);
	}

	/**
	 * Mendapatkan daftar mahasiswa yang diwalikan oleh dosen terlogin
	 *
	 * @param programStudi program studi yang dipilih, atau null jika ingin
	 * mencari dari seluruh program studi yang terdaftar di SIAModels
	 * @param status status mahasiswa yang ingin ditampilkan, atau null jika
	 * default (aktif).
	 * @param angkatan tahun angkatan, atau null jika semua.
	 * @return mahasiswa yang diwalikan
	 * @throws IllegalStateException jika belum login
	 * @throws IOException kesalahan komunikasi
	 */
	public List<Mahasiswa> requestMahasiswaList(Set<ProgramStudi> programStudi, Mahasiswa.Status status, Integer angkatan) throws IllegalStateException, IOException {
		if (token == null) {
			throw new IllegalStateException("Mohon login terlebih dahulu");
		}
		if (programStudi == null) {
			programStudi = new TreeSet<>();
			programStudi.addAll(Arrays.asList(ProgramStudi.values()));
		}
		StringBuilder url = new StringBuilder(SIAKAD_BASE_URL + "/load_table_cari_mahasiswa/");
		boolean first = true;
		for (ProgramStudi ps : programStudi) {
			if (first) {
				first = false;
			} else {
				url.append("-");
			}
			url.append(ps.getSIAKADCode());
		}
		if (status == null) {
			status = Mahasiswa.Status.AKTIF;
		}
		url.append("/").append(status.getSIAKADCode());
		url.append("/").append(angkatan == null ? "00" : angkatan);
		url.append("/0"); // This is for NPM filter, but not used
		Connection connection = createBaseConnection(url.toString(), this.token);
		connection.method(Method.GET);
		Connection.Response response = connection.execute();
		logConnnection(connection);
		Document document = Jsoup.parse(response.body(), response.url().toString());
		List<Mahasiswa> mahasiswaList;
		Elements rows = document.select("#data_table tr");
		// Note: start from 1 as header is skipped.
		mahasiswaList = new ArrayList<>(rows.size() - 1);
		for (int i = 1; i < rows.size(); i++) {
			Elements columns = (rows.get(i)).select("td");
			String npm = columns.get(1).select("a").text();
			String nama = columns.get(2).text();
			Mahasiswa newMahasiswa = new Mahasiswa(npm);
			newMahasiswa.setNama(nama);
			mahasiswaList.add(newMahasiswa);
		}
		return mahasiswaList;
	}

	/**
	 * Mendapatkan data diri mahasiswa. Saat ini hanya mendukung URL foto,
	 * tanggal lahir, dan jenis kelamin tanpa data diri yang lain.
	 *
	 * @param mahasiswa mahasiswa yang ingin diperiksa,
	 * {@link Mahasiswa#getNpm()} tidak boleh null.
	 * @return objek mahasiswa yang sama, dengan data diri yang sudah
	 * dilengkapi
	 * @throws IllegalStateException jika belum login
	 * @throws IOException kesalahan komunikasi
	 * @throws ProtocolException kesalahan format yang diterima dari SIAkad
	 */
	public Mahasiswa requestDataDiri(Mahasiswa mahasiswa) throws IllegalStateException, IOException, ProtocolException {
		if (token == null) {
			throw new IllegalStateException("Mohon login terlebih dahulu");
		}
		Connection connection = createBaseConnection(SIAKAD_BASE_URL + "/data_diri_mahasiswa/" + mahasiswa.getNpm() + "/0", this.token);
		connection.method(Method.GET);
		Connection.Response response = connection.execute();
		logConnnection(connection);
		Document document = Jsoup.parse(response.body(), response.url().toString());
		Element table = document.selectFirst(".portlet.box.blue table.table-condensed");

		// Photo
		Element firstRow = table.selectFirst("tr");
		String photoPath = firstRow.select("img").attr("src");
		mahasiswa.setPhotoPath(photoPath);

		Elements tableCells = table.select("td");
		for (int i = 0; i < tableCells.size(); i++) {
			if (i < tableCells.size() - 2 && tableCells.get(i + 1).text().equals(":")) {
				String fieldName = tableCells.get(i).text();
				String fieldValue = tableCells.get(i + 2).text();
				switch (fieldName) {
					case "Tanggal Lahir":
						StringTokenizer tokenizer = new StringTokenizer(fieldValue);
						int day = Integer.parseInt(tokenizer.nextToken());
						int month = Arrays.asList(MONTH_NAMES).indexOf(tokenizer.nextToken()) + 1;
						if (month < 0) {
							throw new ProtocolException("Month name not recognized in this date: " + fieldValue);
						}
						int year = Integer.parseInt(tokenizer.nextToken());
						mahasiswa.setTanggalLahir(LocalDate.of(year, month, day));
						break;
					case "Jenis Kelamin":
						mahasiswa.setJenisKelamin(JenisKelamin.fromSIAKADCode(fieldValue));
						break;
				}
			}
		}
		return mahasiswa;
	}

	/**
	 * Mendapatkan daftar lengkap riwayat nilai mahasiswa, berdasarkan
	 * halaman "Data Akademik". Metode ini dapat mengisi lengkap kelas,
	 * nilai-nilai Tugas, UTS, UAS, serta NA.
	 *
	 * {@link Mahasiswa#getNpm()} tidak boleh null.
	 *
	 * @param mahasiswa mahasiswa yang ingin diperiksa,
	 * @param includeLastSemester <strong>(rekomendasi: false)</strong> apakah ingin mengikutsertakan nilai semester terakhir yang tercatat.
	 * Kelemahan metode "Data Akademik" adalah tidak bisa membedakan antara nilai yang belum rilis dengan yang sudah.
	 * Jika mahasiswa sedang menjalani mata kuliah tersebut, nilai sudah muncul tetapi kemungkinan NA nya berisi E karena
	 * nilai tugas/UTS/UAS belum lengkap. Jika mahasiswa sedang tidak menjalani kuliah tersebut (misal, saat FRS atau saat libur,
	 * maka nilai semester terakhir perlu diikutsertakan, karena mengacu ke nilai yang sudah rilis). Tentu saja ke depannya perlu
	 * mekanisme yang lebih baik yang dapat mendeteksi nilai yang sudah/belum rilis ini.
	 * 
	 * @return objek mahasiswa yang sama, dengan nilai yang sudah didapatkan
	 * (terurut secara kronologis dari yang paling lama ke baru).
	 * @throws IllegalStateException jika belum login
	 * @throws IOException kesalahan komunikasi
	 * @see {@link #requestRiwayatNilai(Mahasiswa)}
	 */
	public Mahasiswa requestRiwayatNilai(Mahasiswa mahasiswa, boolean includeLastSemester) throws IllegalStateException, IOException {
		if (token == null) {
			throw new IllegalStateException("Mohon login terlebih dahulu");
		}
		// Step 1: Dapatkan semester saat ini
		Connection connection = createBaseConnection(SIAKAD_BASE_URL + "/data_akademik_mahasiswa/" + mahasiswa.getNpm() + "/0", this.token); 
		connection.method(Method.GET);
		Connection.Response response = connection.execute();
		logConnnection(connection);
		Document document = Jsoup.parse(response.body(), response.url().toString());
		Element nilaiSemesterIni = document.select("#nilai_semester_ini_tab").first();
		String nilaiSemesterIniText = nilaiSemesterIni.text().trim();
		TahunSemester tahunSemesterIni = new TahunSemester(nilaiSemesterIniText.substring(nilaiSemesterIniText.length() - 3));		
		
		// Step 2: Dapatkan seluruh nilai
		List<Mahasiswa.Nilai> riwayatNilai = mahasiswa.getRiwayatNilai();
		riwayatNilai.clear();
		connection = createBaseConnection(SIAKAD_BASE_URL + "/load_nilai_per_tahun_semester/" + mahasiswa.getNpm() + "/0", this.token);
		connection.method(Method.GET);
		response = connection.execute();
		logConnnection(connection);
		document = Jsoup.parse(response.body(), response.url().toString());
		Elements tables = document.select("table.table-condensed");
		Pattern tahunAkademikPattern = Pattern.compile("Tahun Akademik (\\d{4})/\\d{4}");
		Pattern semesterPattern = Pattern.compile("Semester (Ganjil|Genap|Pendek)");
		for (Element table : tables) {
			List<String> rowLabels;

			Elements rows = table.select("tr");
			// Row 0, 3: Tahun Akademik, Semester
			String tahunAkademikString = rows.get(0).selectFirst("td").text().trim();
			Matcher tahunAkademikMatcher = tahunAkademikPattern.matcher(tahunAkademikString);
			int tahun;
			if (tahunAkademikMatcher.matches()) {
				tahun = Integer.parseInt(tahunAkademikMatcher.group(1));
			} else {
				throw new IOException("Can't find tahun akademik in SIAkad Output: " + tahunAkademikString);
			}
			String semesterString = rows.get(3).selectFirst("td").text().trim();
			Matcher semesterMatcher = semesterPattern.matcher(semesterString);
			Semester semester;
			if (semesterMatcher.matches()) {
				semester = Semester.fromString(semesterMatcher.group(1));
			} else {
				throw new IOException("Can't find semester in SIAkad Output: " + semesterString);
			}
			TahunSemester tahunSemester = new TahunSemester(tahun, semester);

			// Row 2: Labels
			Elements cols = rows.get(2).select("td");
			rowLabels = new ArrayList<>(cols.size());
			for (Element col : cols) {
				rowLabels.add(col.text());
			}

			// Row 4 to N-1: The grades
			for (int i = 4; i < rows.size() - 1; i++) {
				cols = rows.get(i).select("td");
				String kode = cols.get(1).text();
				String nama = cols.get(2).text();
				int sks = Integer.parseInt(cols.get(3).text());
				Character kelas;
				kelas = cols.get(4).text().length() > 0 ? cols.get(4).text().charAt(0) : null;
				Double uts = null, uas = null;
				Map<String, Double> nilaiTugas = new TreeMap<>();
				for (int j = 0; j < rowLabels.size(); j++) {
					String cellText = cols.get(5 + j).text();
					if (cellText.length() > 0) {
						double cellValue = Double.parseDouble(cellText);
						switch (rowLabels.get(i)) {
							case "UTS":
								uts = cellValue;
								break;
							case "UAS":
								uas = cellValue;
								break;
							default:
								nilaiTugas.put(rowLabels.get(j), cellValue);
								break;
						}
					}
				}
				String nilaiAkhir = cols.get(cols.size() - 1).text();
				if (nilaiAkhir.length() == 0) {
					nilaiAkhir = null;
				}
				if (includeLastSemester || !tahunSemester.equals(tahunSemesterIni)) {
					// Exclude nilai from current tahun/semester be cause most likely it's yet to be released
					Mahasiswa.Nilai nilai = new Mahasiswa.Nilai(tahunSemester,
						MataKuliahFactory.getInstance().createMataKuliah(kode, sks, nama), kelas, nilaiTugas, uts, uas, nilaiAkhir);
					riwayatNilai.add(nilai);
				}
			}
		}
		return mahasiswa;
	}

	/**
	 * Create the base connection, with logged in state..
	 *
	 * @param url URL to connect
	 * @param token the auth token, if any
	 * @return the connection
	 */
	protected Connection createBaseConnection(String url, Token token) {
		Connection connection = Jsoup.connect(url);
		if (token != null) {
			token.injectToConnection(connection);
		}
		connection.timeout(DEFAULT_TIMEOUT);
		return connection;
	}

	/**
	 * This method checks the verbose flag, and when true will print debug
	 * output.
	 *
	 * @param connection the connection request and response
	 */
	protected void logConnnection(Connection connection) {
		logger.log(Level.FINE, "Request: {0} {1} {2} {3} {4}\nResponse: {5} {6}\nBody: {7}\n===\n",
			new Object[]{connection.request().method(),
				connection.request().url(),
				connection.request().headers(),
				connection.request().data(),
				connection.request().cookies(),
				connection.response().statusCode(),
				connection.response().headers(),
				connection.response().body()});
	}
}

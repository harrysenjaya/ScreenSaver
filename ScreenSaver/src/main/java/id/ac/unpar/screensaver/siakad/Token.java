package id.ac.unpar.screensaver.siakad;

import java.util.Map;
import org.jsoup.Connection;

public class Token {

	private final Map<String, String> cookies;

	public Token(Map<String, String> cookies) {
		this.cookies = cookies;
	}

	public Map<String, String> getCookies() {
		return cookies;
	}

	/**
	 * Inject this session into a Jsoup connection
	 *
	 * @param connection the connection to inject
	 */
	public void injectToConnection(Connection connection) {
		cookies.entrySet().forEach((cookie) -> {
			connection.cookie(cookie.getKey(), cookie.getValue());
		});
	}
}

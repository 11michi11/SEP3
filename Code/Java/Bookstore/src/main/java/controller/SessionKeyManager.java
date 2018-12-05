package controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

@Component
public class SessionKeyManager {

	private HashMap<String, Calendar> sessionKeys = new HashMap<>();
	private static final String URL = "https://localhost:8080/checkSK/";

	public boolean isSKValid(String sessionKey) throws SessionKeyInvalidException {
		Calendar expirationDate = sessionKeys.get(sessionKey);
		if (expirationDate == null) {
			expirationDate = checkInBookService(sessionKey);
			sessionKeys.put(sessionKey, expirationDate);
		}

		Calendar now = GregorianCalendar.getInstance();
		return now.before(expirationDate);
	}

	private Calendar checkInBookService(String sessionKey) throws SessionKeyInvalidException {
		try {
			String response = makeRequest(URL + sessionKey);
			Gson gson = new Gson();
			return gson.fromJson(response, GregorianCalendar.class);
		} catch (IOException e) {
			throw new SessionKeyInvalidException("Session can not be authenticated");
		}
	}

	private static String makeRequest(String requestUrl) throws IOException, SessionKeyInvalidException {
		URL url = new URL(requestUrl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuilder response = new StringBuilder();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		if(responseCode== 401)
			throw new SessionKeyInvalidException("Session is invalid");
		return response.toString();
	}

	public static class SessionKeyInvalidException extends Exception{
		public SessionKeyInvalidException(String msg) {
			super(msg);
		}
	}
}

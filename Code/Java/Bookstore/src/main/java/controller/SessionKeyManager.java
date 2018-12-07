package controller;

import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class SessionKeyManager {

	private HashMap<String, Calendar> sessionKeys = new HashMap<>();
	private static final String URL = "https://localhost:8080/checkSK/";

	public void isSessionKeyValid(String sessionKey) throws SessionKeyInvalidException {
		Calendar expirationDate = sessionKeys.get(sessionKey);
		if (expirationDate == null) {
			expirationDate = checkInBookService(sessionKey);
			sessionKeys.put(sessionKey, expirationDate);
		}

		Calendar now = GregorianCalendar.getInstance(TimeZone.getDefault());
		System.out.println(now.getTime());
		if (!now.before(expirationDate))
			throw new SessionKeyInvalidException("The session key is not valid. Session can not be authenticated");
	}

	private Calendar checkInBookService(String sessionKey) throws SessionKeyInvalidException {
		try {
			String response = makeRequest(URL + sessionKey);
			DateFormat df = new SimpleDateFormat("yyyy MMM dd HH:mm:ss");
			df.setTimeZone(TimeZone.getDefault());
			Date date = df.parse(response);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			return cal;
		} catch (IOException e) {
			e.printStackTrace();
			throw new SessionKeyInvalidException("Session can not be authenticated");
		} catch (ParseException e) {
			e.printStackTrace();
			throw new SessionKeyInvalidException("Invalid expiration data format");
		}
	}

	private String makeRequest(String requestUrl) throws IOException, SessionKeyInvalidException {
		URL url = new URL(requestUrl);
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
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


	public static class SessionKeyInvalidException extends RuntimeException{
		public SessionKeyInvalidException(String msg) {
			super(msg);
		}
	}
}

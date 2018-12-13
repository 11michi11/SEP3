package controller;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class SessionKeyManager {

	private HashMap<String, Calendar> sessionKeys = new HashMap<>();
	private static final String URL = "https://localhost:8080/";
	private final String BOOKSTORE_ID = "eb3777c8-77fe-4acd-962d-6853da2e05e0";

	public void deleteFromCache(String sessionKey) {
		sessionKeys.remove(sessionKey);
		deleteFromBookService(sessionKey);
	}

	private void deleteFromBookService(String sessionKey) {
		BasicCookieStore cookieStore = new BasicCookieStore();
		BasicClientCookie cookie = new BasicClientCookie("sessionKey", sessionKey);
		cookie.setDomain("localhost");
		cookie.setPath("/");
		cookieStore.addCookie(cookie);

		DefaultHttpClient client = new DefaultHttpClient();
		client.setCookieStore(cookieStore);

		HttpDelete request = new HttpDelete(URL + "logOut");
		HttpContext localContext = new BasicHttpContext();
		localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
		try {
			HttpResponse response = client.execute(request, localContext);
			System.out.println("\nSending 'DELETE' request to URL : " + request.getURI());
			System.out.println("Response Code : " + response.getStatusLine());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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
			String response = makeRequest(URL + "checkSK/" + sessionKey+"/"+BOOKSTORE_ID);
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

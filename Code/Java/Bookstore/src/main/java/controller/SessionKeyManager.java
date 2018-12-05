package controller;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.Certificate;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

		Calendar now = GregorianCalendar.getInstance(TimeZone.getDefault());
		System.out.println(now.getTime());
		return now.before(expirationDate);
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

	private void print_https_cert(HttpsURLConnection con){

		if(con!=null){

			try {

				System.out.println("Response Code : " + con.getResponseCode());
				System.out.println("Cipher Suite : " + con.getCipherSuite());
				System.out.println("\n");

				Certificate[] certs = con.getServerCertificates();
				for(Certificate cert : certs){
					System.out.println("Cert Type : " + cert.getType());
					System.out.println("Cert Hash Code : " + cert.hashCode());
					System.out.println("Cert Public Key Algorithm : "
							+ cert.getPublicKey().getAlgorithm());
					System.out.println("Cert Public Key Format : "
							+ cert.getPublicKey().getFormat());
					System.out.println("\n");
				}

			} catch (SSLPeerUnverifiedException e) {
				e.printStackTrace();
			} catch (IOException e){
				e.printStackTrace();
			}

		}

	}

	private void print_content(HttpsURLConnection con){
		if(con!=null){

			try {

				System.out.println("****** Content of the URL ********");
				BufferedReader br =
						new BufferedReader(
								new InputStreamReader(con.getInputStream()));

				String input;

				while ((input = br.readLine()) != null){
					System.out.println(input);
				}
				br.close();

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}



	public static class SessionKeyInvalidException extends Exception{
		public SessionKeyInvalidException(String msg) {
			super(msg);
		}
	}
}

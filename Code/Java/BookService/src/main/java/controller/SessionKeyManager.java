package controller;

import java.util.*;

public class SessionKeyManager {
	static HashMap<String,Calendar> sessionKeys = new HashMap<>();

	public static String generateSK() {
		String sessionKey = UUID.randomUUID().toString();
		Calendar expirationDate = GregorianCalendar.getInstance(TimeZone.getDefault());
		expirationDate.add(Calendar.HOUR,1);
		sessionKeys.put(sessionKey, expirationDate);
		return sessionKey;
	}

	public static Calendar checkSK(String sessionKey) throws SessionKeyIsNotValidException {
		Calendar expDate = sessionKeys.get(sessionKey);
		if (expDate == null) {
			throw new SessionKeyIsNotValidException("The session key is not valid");
		}
		return expDate;
	}

	public static class SessionKeyIsNotValidException extends RuntimeException {

		public SessionKeyIsNotValidException(String message) {
			super(message);
		}
	}
}

package controller;

import java.util.*;

public class SessionKeyManager {
	static HashMap<String,Calendar> sessionKeys = new HashMap<>();
	static{
		Calendar expirationDate = GregorianCalendar.getInstance(TimeZone.getDefault());
		expirationDate.add(Calendar.DAY_OF_MONTH,1);
		sessionKeys.put("9440b6fe-b4c4-4dab-b13b-99c37ec173ed", expirationDate);
	}

	public static String generateSK() {
		String sessionKey = UUID.randomUUID().toString();
		Calendar expirationDate = GregorianCalendar.getInstance(TimeZone.getDefault());
		expirationDate.add(Calendar.HOUR,1);
		sessionKeys.put(sessionKey, expirationDate);
		return sessionKey;
	}

	public static Calendar checkSKFromInstitution(String sessionKey) throws SessionKeyIsNotValidException {
		Calendar expDate = sessionKeys.get(sessionKey);
		if (expDate == null) {
			throw new SessionKeyIsNotValidException("The session key is not valid");
		}
		Calendar now = GregorianCalendar.getInstance(TimeZone.getDefault());
		System.out.println(now.getTime());
		if(now.before(expDate))
			return expDate;
		throw new SessionKeyIsNotValidException("The session key is not valid");
	}

	public static void checkSessionKey(String sessionKey) throws SessionKeyIsNotValidException {
		Calendar expDate = sessionKeys.get(sessionKey);
		if (expDate == null) {
			throw new SessionKeyIsNotValidException("The session key is not valid");
		}
		Calendar now = GregorianCalendar.getInstance(TimeZone.getDefault());
		System.out.println(now.getTime());
		if (!now.before(expDate))
			throw new SessionKeyIsNotValidException("The session key is not valid");
	}

	public static class SessionKeyIsNotValidException extends RuntimeException {

		public SessionKeyIsNotValidException(String message) {
			super(message);
		}
	}
}

package controller;

import java.util.Calendar;
import java.util.HashMap;

public class SessionKeyManager {
	static HashMap<String,Calendar> sessionKeys;

	public static String generateSK() {
		return "New session key";
	}

	public static Calendar checkSK(String sessionKey) throws SessionKeyIsNotValidException {
		Calendar expDate = sessionKeys.get(sessionKey);
		if (expDate == null) {
			throw new SessionKeyIsNotValidException("The session key is not valid");
		}
		return expDate;
	}

	public static class SessionKeyIsNotValidException extends Throwable {

		public SessionKeyIsNotValidException(String message) {
			super(message);
		}
	}
}

package controller;

import model.AuthenticationData;

import java.util.*;

public class SessionKeyManager {
	static HashMap<String,AuthenticationData> sessionKeys = new HashMap<>();

	static{
		Calendar expirationDate = GregorianCalendar.getInstance(TimeZone.getDefault());
		expirationDate.add(Calendar.DAY_OF_MONTH,1);
		AuthenticationData data =  new AuthenticationData("bookservice", AuthenticationData.UserType.All, expirationDate);
		sessionKeys.put("9440b6fe-b4c4-4dab-b13b-99c37ec173ed",data);
	}

	public static String generateSK(String institutionId, AuthenticationData.UserType userType) {
		String sessionKey= UUID.randomUUID().toString();
		Calendar expirationDate = GregorianCalendar.getInstance(TimeZone.getDefault());
		expirationDate.add(Calendar.HOUR,1);
		AuthenticationData data = new AuthenticationData(institutionId, userType, expirationDate);
		sessionKeys.put(sessionKey, data);
		return sessionKey;
	}

	public static Calendar checkSKFromInstitution(String sessionKey, String institutionId) throws SessionKeyIsNotValidException {
		AuthenticationData data = sessionKeys.get(sessionKey);
		if (data == null) {
			throw new SessionKeyIsNotValidException("The session key is not valid");
		}
		if(data.authenticateFromInstitution(institutionId))
			return data.expirationDate;
		throw new SessionKeyIsNotValidException("The session key is not valid");
	}

	public static void checkSessionKey(String sessionKey) throws SessionKeyIsNotValidException {
		AuthenticationData data = sessionKeys.get(sessionKey);
		if (data == null) {
			throw new SessionKeyIsNotValidException("The session key is not valid");
		}

		if (!data.authenticate())
			throw new SessionKeyIsNotValidException("The session key is not valid");
	}

	public static class SessionKeyIsNotValidException extends RuntimeException {

		public SessionKeyIsNotValidException(String message) {
			super(message);
		}
	}
}

package model;

public class LogInResponse {
	private String url;
	private String sessionKey;
	private UserType userType;

	public LogInResponse(String url,UserType userType) {
		this.url = url;
		this.sessionKey = null;
		this.userType = userType;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

}

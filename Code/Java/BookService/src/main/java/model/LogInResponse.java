package model;

public class LogInResponse {
	private String url;
	private String sessionKey;
	private String userType;

	public LogInResponse(String url,String userType) {
		this.url = url;
		this.sessionKey = null;
		this.userType = userType;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	@Override
	public String toString() {
		return "LogInResponse{" +
				"url='" + url + '\'' +
				", sessionKey='" + sessionKey + '\'' +
				", userType='" + userType + '\'' +
				'}';
	}
}

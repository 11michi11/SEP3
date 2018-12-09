package model;

public class LogInResponse {
	private String url;
	private String sessionKey;
	private String userType;
	private String name;

	public LogInResponse(String url,String userType, String name) {
		this.url = url;
		this.sessionKey = null;
		this.userType = userType;
		this.name = name;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "LogInResponse{" +
				"url='" + url + '\'' +
				", sessionKey='" + sessionKey + '\'' +
				", userType='" + userType + '\'' +
				", name='" + name + '\'' +
				'}';
	}
}

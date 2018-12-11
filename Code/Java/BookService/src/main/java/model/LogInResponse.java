package model;

public class LogInResponse {
	private String institutionId;
	private String url;
	private String sessionKey;
	private String userType;
	private String name;
	private String userId;

	public LogInResponse(String url, String userType, String name, String institutionId, String userId) {
		this.url = url;
		this.userId = userId;
		this.sessionKey = null;
		this.userType = userType;
		this.name = name;
		this.institutionId = institutionId;
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

	public String getUserId() {return userId;}

	public void setUserId(String userId) { this.userId = userId;}

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

	public String getInstitutionId() {
		return institutionId;
	}

	public void setInstitutionId(String institutionId) {
		this.institutionId = institutionId;
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

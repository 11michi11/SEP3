package communication;

public class LogInResponse {
    private String institutionId;
    private String name;
    private String url;
    private String sessionKey;
    private String userType;
    private String userId;

    public LogInResponse(String url, String userType, String name, String institutionId, String userId) {
        this.url = url;
        this.userId = userId;
        this.sessionKey ="empty";
        this.userType = userType;
        this.name = name;
        this.institutionId = institutionId;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

}

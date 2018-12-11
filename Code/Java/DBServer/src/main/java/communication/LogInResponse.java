package communication;

public class LogInResponse {
    private String institutionId;
    private String name;
    private String url;
    private String sessionKey;
    private String userType;

    public LogInResponse(String url,String userType, String name, String institutionId) {
        this.url = url;
        this.sessionKey ="empty";
        this.userType = userType;
        this.name = name;
        this.institutionId = institutionId;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

}

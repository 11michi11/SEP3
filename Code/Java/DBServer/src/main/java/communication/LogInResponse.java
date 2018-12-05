package communication;

public class LogInResponse {
    private String url;
    private String sessionKey;
    private String userType;

    public LogInResponse(String url,String userType) {
        this.url = url;
        this.sessionKey ="empty";
        this.userType = userType;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

}

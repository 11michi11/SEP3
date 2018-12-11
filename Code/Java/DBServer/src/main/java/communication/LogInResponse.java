package communication;

public class LogInResponse {
    private String institutionId;
    private String name;
    private String url;
    private String sessionKey;
    private String userType;
    private String customerId;

    public LogInResponse(String url, String userType, String name, String institutionId, String customerId) {
        this.url = url;
        this.customerId = customerId;
        this.sessionKey ="empty";
        this.userType = userType;
        this.name = name;
        this.institutionId = institutionId;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

}

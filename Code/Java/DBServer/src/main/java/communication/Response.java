package communication;

import com.google.gson.Gson;

public class Response {

    public enum Status {OK, Error}

    private Status status;
    private Object content;

    public Response(Status status, Object content) {
        this.status = status;
        this.content = content;
    }

    public String toJson() {
        Gson gson = new Gson();

        return gson.toJson(this);
    }

}
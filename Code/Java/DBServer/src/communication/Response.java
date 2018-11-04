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

/*
JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(json);
        JsonObject obj = element.getAsJsonObject(); //since you know it's a JsonObject
        Set<Map.Entry<String, JsonElement>> entries = obj.entrySet();//will return members of your object
        for (Map.Entry<String, JsonElement> entry: entries) {
            System.out.println(entry.getKey());
        }
 */



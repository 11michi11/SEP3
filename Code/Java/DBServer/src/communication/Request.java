package communication;

import com.google.gson.Gson;
import model.Book;

import java.util.HashMap;
import java.util.Map;

public class Request {

    public enum Operation{Search, AdvancedSearch}

    private Operation operation;
    private Map<String, Object> args;

    public Request(Operation operation, Map<String, Object> args) {
        this.operation = operation;
        this.args = args;
    }

    public static void main(String[] args) {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("isbn", "isbn");
        arguments.put("title", "Lord of the Rings");
        arguments.put("author", "Tolkien");
        arguments.put("year", 2000);
        arguments.put("category", Book.Category.Fantasy);
        Request request = new Request(Operation.AdvancedSearch, arguments);
        Gson gson = new Gson();
        String json = request.toJson();
        Request request1 = gson.fromJson(json, Request.class);
        System.out.println(json);
    }

    private Map<String, Object> getArguments() {
        return args;
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public Request fromJson(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, Request.class);
    }

}

package controller.connection;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.Map;

public class Request {

    public enum Operation {BookStoreSearch, BookStoreAdvancedSearch, AddBook, DeleteBook, BookDetails}

    private Operation operation;
    private Map<String, Object> args;

    public Request(Operation operation, Map<String, Object> args) {
        this.operation = operation;
        this.args = args;
    }

    public Operation getOperation() {
        return operation;
    }

    public Map<String, Object> getArguments() {
        return args;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static Request fromJson(String json) throws RequestJsonFormatException {
        try {
            Gson gson = new Gson();
            return gson.fromJson(json, Request.class);
        } catch (JsonSyntaxException e) {
            throw new RequestJsonFormatException("Wrong format of Request JSON");
        }
    }

    public static class RequestJsonFormatException extends Exception {
        RequestJsonFormatException(String msg) {
            super(msg);
        }
    }
}

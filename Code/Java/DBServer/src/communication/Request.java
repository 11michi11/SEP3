package communication;

import com.google.gson.Gson;
import model.Book;

import java.util.Arrays;
import java.util.Map;
import java.util.regex.Pattern;

public class Request {

    public enum Operation{Search, AdvancedSearch}

    private Operation operation;
    private RequestArgs args;

    public Request(Operation operation, RequestArgs wrapper) {
        this.operation = operation;
        this.args = wrapper;
    }

    public static void main(String[] args) {
        Request request = new Request(Operation.AdvancedSearch, new AdvencedSearch("isbn", "Lord of the Rings", "author", 2000, Book.Category.Fantasy));
        Gson gson = new Gson();
        String json = request.toJson();
       // Request request1 = gson.fromJson(json, Request.class);
        System.out.println(json);
        Request.fromJson(json);
    }

    private Map<String, Object> getArguments() {
        return args.getArguments();
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static Request fromJson(String json){
        //Example JSON
        //{"operation":"Search","args":{"searchTerm":"Lord of the Rings"}}
        //{"operation":"AdvancedSearch","args":{"isbn":"isbn","title":"Lord of the Rings","author":"author","year":2000,"category":"Fantasy"}}
        String[] splited = splitJSON(json);
        Operation operation = Operation.valueOf(splited[0]);
        switch(operation){
            case Search:
                return parseSearch(splited[1]);
            case AdvancedSearch:
                return parseAdvancedSearch(splited[1]);
        }
        return null;
    }

    private static Request parseSearch(String x) {
        //Cut of search term
        String searchTerm = x.substring(14, x.length()-1);

        return new Request(Operation.Search,new Search(searchTerm));
    }

    private static Request parseAdvancedSearch(String arguments) {
        //Split arguments
        String[] args = arguments.split(Pattern.quote(","));
        //Extract values
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            args[i] = arg.split(":")[1];
        }
        //Extract args
        String isbn = args[0].substring(1, args[0].length()-1);
        String title = args[1].substring(1, args[1].length()-1);
        String author = args[2].substring(1, args[2].length()-1);
        int year = Integer.parseInt(args[3]);
        Book.Category category = Book.Category.valueOf(args[0].substring(1, args[0].length()-1));

        return new Request(Operation.AdvancedSearch, new AdvencedSearch(isbn, title, author, year, category));
    }

    private static String[] splitJSON(String json){
        //Split the type and args
        String[] parsedRequest = json.substring(1, json.length()-1).split(Pattern.quote("\",\"args\":"));
        System.out.println(Arrays.toString(parsedRequest));
        //Cut out brackets and quotes
        parsedRequest[0] = parsedRequest[0].substring(13);
        parsedRequest[1] = parsedRequest[1].substring(1,parsedRequest[1].length()-1 );
        return parsedRequest;
    }


}

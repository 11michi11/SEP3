package controller.connection;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import model.Book;
import org.springframework.stereotype.Component;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DatabaseConnection implements DatabaseProxy {

    private final int PORT = 7777;
//        private final String IP = "207.154.237.196";
    private final String IP = "localhost";

    private final String BOOKSTORE_ID = "eb3777c8-77fe-4acd-962d-6853da2e05e0";


    public List<Book> search(String searchTerm) throws ServerOfflineException, SearchException {
        Map<String, Object> args = new HashMap<>();
        args.put("searchTerm", searchTerm);
        args.put("bookstoreid", BOOKSTORE_ID);

        Request request = new Request(Request.Operation.BookStoreSearch, args);

        String response = sendMessage(request);
        ResponseStatus status = getResponseStatus(response);
        return handleSearchResponse(response, status);
    }

    private List<Book> handleSearchResponse(String response, ResponseStatus status) throws SearchException {
        switch (status) {
            case OK:
                return getContent(response);
            case Error:
                String errorMsg = getContent(response);
                throw new SearchException("Database returned error: " + errorMsg);
            default:
                throw new SearchException("Unknown response status: " + status);
        }
    }

    public String getBookDetails(String isbn) throws ServerOfflineException, SearchException {
        Map<String, Object> args = new HashMap<>();
        args.put("isbn", isbn);
        Request request = new Request(Request.Operation.BookDetails, args);

        String response = sendMessage(request);
        ResponseStatus status = getResponseStatus(response);
        return handleBookDetailsResponse(response, status);
    }

    private String handleBookDetailsResponse(String response, ResponseStatus status) {
        switch (status) {
            case OK:
                return getContent(response);
            case Error:
                String errorMsg = getContent(response);
                throw new SearchException("Database returned error: " + errorMsg);
            default:
                throw new SearchException("Unknown response status: " + status);
        }
    }

    public List<Book> advancedSearch(String title, String author, int year, String isbn, Book.Category category) throws ServerOfflineException, SearchException {
        Map<String, Object> args = new HashMap<>();
        args.put("isbn", isbn);
        args.put("title", title);
        args.put("author", author);
        args.put("year", year);
        args.put("category", category);

        args.put("bookstoreid", BOOKSTORE_ID);
        Request request = new Request(Request.Operation.BookStoreAdvancedSearch, args);

        String response = sendMessage(request);
        ResponseStatus status = getResponseStatus(response);
        return handleSearchResponse(response, status);
    }

    private ResponseStatus getResponseStatus(String response) {
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(response);
        JsonObject obj = element.getAsJsonObject(); //since you know it's a JsonObject
        return ResponseStatus.valueOf(obj.get("status").getAsString());
        // Getting all keys
//		Set<Map.Entry<String, JsonElement>> entries = obj.entrySet();//will return members of your object
//		for (Map.Entry<String, JsonElement> entry: entries) {
//			System.out.println(entry.getKey());
//		}
    }

    private <T> T getContent(String contentJson) {
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(contentJson);
        JsonObject obj = element.getAsJsonObject(); //since you know it's a JsonObject
        String content = obj.get("content").toString();
        Type type = new TypeToken<T>() {
        }.getType();
        Gson gson = new Gson();
        return gson.fromJson(content, type);
    }

    private String sendMessage(Request request) throws ServerOfflineException {
        try {
            Socket server = openConnection();

            try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()));
                 BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()))) {

                System.out.println("Sending request to db: " + request);
                out.write(request.toJson() + "\n");
                out.flush();
                in.ready();
                return in.readLine();
            } catch (IOException e) {
                throw new ServerOfflineException("Could not send msg");
            }
        } catch (IOException e) {
            throw new ServerOfflineException("Could not connect to server");
        }
    }

    private Socket openConnection() throws IOException {
        System.out.println("Trying to connect..");
        Socket server = new Socket(IP, PORT);
        System.out.println("Connected!");
        return server;
    }

    @Override
    public String addBook(Book book) {
        Map<String, Object> args = new HashMap<>();
        args.put("library", false);
        args.put("id", BOOKSTORE_ID);
        args.put("book", book);

        Request request = new Request(Request.Operation.AddBook, args);

        String response = sendMessage(request);
        ResponseStatus status = getResponseStatus(response);
        if (status.equals(ResponseStatus.OK))
            return "OK";
        return "Error: " + getContent(response);
    }

    @Override
    public String deleteBook(String isbn) {
        Map<String, Object> args = new HashMap<>();
        args.put("library", false);
        args.put("id", BOOKSTORE_ID);
        args.put("isbn", isbn);

        Request request = new Request(Request.Operation.DeleteBook, args);

        String response = sendMessage(request);
        ResponseStatus status = getResponseStatus(response);
        if (status.equals(ResponseStatus.OK))
            return "OK";
        return "Error: " + getContent(response);
    }

    private enum ResponseStatus {OK, Error}

    public class ServerOfflineException extends RuntimeException {
        public ServerOfflineException(String msg) {
            super(msg);
        }

    }

    public class SearchException extends RuntimeException {
        public SearchException(String msg) {
            super(msg);
        }
    }
}

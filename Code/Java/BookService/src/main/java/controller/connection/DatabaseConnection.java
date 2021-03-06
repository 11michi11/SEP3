package controller.connection;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import controller.ConfigurationLoader;
import model.Book;
import model.Customer;
import model.LogInResponse;
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
    private final String IP = "localhost";//ConfigurationLoader.getDbAddress();
    private static Gson gson = new Gson();

    public List<Book> search(String searchTerm) throws ServerOfflineException, SearchException {
        Map<String, Object> args = new HashMap<>();
        args.put("searchTerm", searchTerm);
        Request request = new Request(Request.Operation.Search, args);

        String response = sendMessage(request);
        ResponseStatus status = getResponseStatus(response);
        return handleSearchResponse(response, status);
    }

    public List<Book> advancedSearch(String title, String author, int year, String isbn, Book.Category category) throws ServerOfflineException, SearchException {
        Map<String, Object> args = new HashMap<>();
        args.put("isbn", isbn);
        args.put("title", title);
        args.put("author", author);
        args.put("year", year);
        args.put("category", category);
        Request request = new Request(Request.Operation.AdvancedSearch, args);

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
                LinkedTreeMap content = getContent(response);
                JsonElement jsonElement = gson.toJsonTree(content);
                String asString = jsonElement.toString();
                return asString;
            case Error:
                String errorMsg = getContent(response);
                throw new SearchException("Database returned error: " + errorMsg);
            default:
                throw new SearchException("Unknown response status: " + status);
        }
    }

    @Override
    public String borrowBook(String isbn, String libraryID, String customerID) {
        Map<String, Object> args = new HashMap<>();
        args.put("isbn", isbn);
        args.put("libraryId", libraryID);
        args.put("customerId", customerID);
        Request request = new Request(Request.Operation.MakeLibraryOrder, args);

        return sendMessage(request);
    }

    @Override
    public String buyBook(String isbn, String bookstoreID, String customerID) {
        Map<String, Object> args = new HashMap<>();
        args.put("bookstoreId", bookstoreID);
        args.put("isbn", isbn);
        args.put("customerId", customerID);
        Request request = new Request(Request.Operation.MakeBookStoreOrder, args);

        return sendMessage(request);
    }

    @Override
    public LogInResponse logIn(String email, String password) throws LoginException {
        Map<String, Object> args = new HashMap<>();
        args.put("email", email);
        args.put("password", password);
        Request request = new Request(Request.Operation.Authenticate, args);

        String response = sendMessage(request);
        ResponseStatus status = getResponseStatus(response);
        return handleLogInResponse(response, status);
    }

    private LogInResponse handleLogInResponse(String response, ResponseStatus status) throws LoginException {
        switch (status) {
            case OK:
                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(response);
                JsonObject obj = element.getAsJsonObject(); //since you know it's a JsonObject
                String content = obj.get("content").toString();
                Type type = new TypeToken<LogInResponse>() {
                }.getType();
                LogInResponse res = gson.fromJson(content, type);
                return res;
            case Error:
                String errorMsg = getContent(response);
                throw new LoginException(errorMsg);
            default:
                throw new LoginException("Unknown response status: " + status);
        }
    }

    public String addCustomer(Customer customer) throws RegisterEmailException {
        Map<String, Object> args = new HashMap<>();
        args.put("name", customer.getName());
        args.put("email", customer.getEmail());
        args.put("address", customer.getAddress());
        args.put("phoneNum", customer.getPhoneNum());
        args.put("password", customer.getPassword());

        Request request = new Request(Request.Operation.RegisterCustomer, args);

        String response = sendMessage(request);
        ResponseStatus status = getResponseStatus(response);
        if (status.equals(ResponseStatus.OK))
            return response;
        else throw new RegisterEmailException("Email already in use");
    }

    private ResponseStatus getResponseStatus(String response) {
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(response);
        JsonObject obj = element.getAsJsonObject(); //since you know it's a JsonObject
        return ResponseStatus.valueOf(obj.get("status").getAsString());
    }

    private static <T> T getContent(String contentJson) {
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(contentJson);
        JsonObject obj = element.getAsJsonObject(); //since you know it's a JsonObject
        String content = obj.get("content").toString();
        Type type = new TypeToken<T>() {}.getType();
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

    public class ServerOfflineException extends RuntimeException {
        public ServerOfflineException(String msg) {
            super(msg);
        }
    }

    private enum ResponseStatus {OK, Error}

    public class SearchException extends RuntimeException {
        public SearchException(String msg) {
            super(msg);
        }
    }

    public class LoginException extends RuntimeException{
        public LoginException(String errorMsg) {
            super(errorMsg);
        }
    }

    public class RegisterEmailException extends RuntimeException {
        public RegisterEmailException(String msg) {
            super(msg);
        }
    }
}

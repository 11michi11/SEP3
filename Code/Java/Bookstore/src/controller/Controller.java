package controller;

import com.google.gson.Gson;
import controller.connection.DatabaseConnection;
import controller.connection.DatabaseProxy;
import model.Book;

public class Controller {

    private Controller instance;
    private DatabaseProxy db;
    private Gson gson = new Gson();

    private Controller() {
        this.db = new DatabaseConnection();
    }

    public Controller getInstance() {
        if (instance == null)
            instance = new Controller();
        return instance;
    }

    public String search(String searchTerm) throws DatabaseConnection.ServerOfflineException, DatabaseConnection.SearchException {
        return gson.toJson(db.search(searchTerm));
    }

    public String advancedSearch(String title, String author, int year, String isbn, Book.Category category) throws DatabaseConnection.ServerOfflineException, DatabaseConnection.SearchException {
        return gson.toJson(db.advancedSearch(title, author, year, isbn, category));
    }

    public static void main(String[] args) {
        Controller controller = new Controller();
        try {
            System.out.println(controller.search("Tolkien"));
        } catch (DatabaseConnection.ServerOfflineException | DatabaseConnection.SearchException e) {
            e.printStackTrace();
        }
    }
}

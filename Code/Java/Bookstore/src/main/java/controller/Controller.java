package controller;

import com.google.gson.Gson;
import controller.connection.DatabaseConnection;
import controller.connection.DatabaseProxy;
import controller.connection.MockDatabase;
import model.Book;

import java.util.List;

public class Controller {

    private static Controller instance;
    private DatabaseProxy db;
    private Gson gson = new Gson();

    Controller() {
        this.db = new DatabaseConnection();
    }

    public static Controller getInstance() {
        if (instance == null)
            instance = new Controller();
        return instance;
    }

    public List<Book> search(String searchTerm) throws DatabaseConnection.ServerOfflineException, DatabaseConnection.SearchException {
        return db.search(searchTerm);
    }

    public List<Book> advancedSearch(String title, String author, int year, String isbn, Book.Category category) throws DatabaseConnection.ServerOfflineException, DatabaseConnection.SearchException {
        return db.advancedSearch(title, author, year, isbn, category);
    }

    public static void main(String[] args) {
        Controller controller = new Controller();
        try {
            System.out.println(controller.search("Tolkien"));
        } catch (DatabaseConnection.ServerOfflineException | DatabaseConnection.SearchException e) {
            e.printStackTrace();
        }
    }

    public String getBookDetails(String isbn) {
        return db.getBookDetails(isbn);
    }

    public Book addBook(Book book){
        return db.addBook(book);
    }
}

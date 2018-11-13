package controller;

import com.google.gson.Gson;
import controller.connection.DatabaseConnection;
import controller.connection.DatabaseProxy;
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

    public String getBookDetails(String isbn) {
        return db.getBookDetails(isbn);
    }

    public String addBook(Book book){
        return db.addBook(book);
    }

	public String deleteBook(String isbn) {
		return db.deleteBook(isbn);
	}


    public static void main(String[] args) {
        Controller controller = new Controller();
        try {
            Book book = new Book("978-0134685991", "Effective Java", "Joshua Bloch", 2017, Book.Category.Science);
            System.out.println(controller.deleteBook("978-0134685991"));
        } catch (DatabaseConnection.ServerOfflineException | DatabaseConnection.SearchException e) {
            e.printStackTrace();
        }
    }
}

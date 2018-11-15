package controller;

import com.google.gson.Gson;
import controller.connection.DatabaseConnection;
import controller.connection.DatabaseProxy;
import model.Book;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Controller {

    private DatabaseProxy db;
    private Gson gson = new Gson();

    @Autowired
    Controller(DatabaseProxy db) {
        this.db = db;
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
        DatabaseConnection db = new DatabaseConnection();
        Controller controller = new Controller(db);
        try {
            Book book = new Book("978-0134685991", "Effective Java", "Joshua Bloch", 2017, Book.Category.Science);
            System.out.println(controller.deleteBook("978-0134685991"));
        } catch (DatabaseConnection.ServerOfflineException | DatabaseConnection.SearchException e) {
            e.printStackTrace();
        }
    }


}

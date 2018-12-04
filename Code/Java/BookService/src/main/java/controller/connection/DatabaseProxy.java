package controller.connection;

import model.Book;
import model.Customer;
import model.DetailedBook;
import model.LogInResponse;

import javax.security.auth.login.LoginException;
import java.util.List;

public interface DatabaseProxy {
	String getBookDetails(String isbn);
	List<Book> search(String searchTerm) throws DatabaseConnection.ServerOfflineException, DatabaseConnection.SearchException;
	List<Book> advancedSearch(String title, String author, int year, String isbn, Book.Category category) throws DatabaseConnection.ServerOfflineException, DatabaseConnection.SearchException;
	String addCustomer(Customer customer);
	String borrowBook(String bookID, String libraryID, String customerID);
	String buyBook(String isbn, String bookstoreID, String customerID);

	LogInResponse logIn(String email, String password) throws LoginException;
}

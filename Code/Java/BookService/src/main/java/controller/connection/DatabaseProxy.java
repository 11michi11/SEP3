package controller.connection;

import model.Book;
import model.Customer;
import model.LogInResponse;

import java.util.List;

public interface DatabaseProxy {
	String getBookDetails(String isbn);
	List<Book> search(String searchTerm) throws DatabaseConnection.ServerOfflineException, DatabaseConnection.SearchException;
	List<Book> advancedSearch(String title, String author, int year, String isbn, Book.Category category) throws DatabaseConnection.ServerOfflineException, DatabaseConnection.SearchException;
	String addCustomer(Customer customer) throws DatabaseConnection.RegisterEmailException;
	String borrowBook(String bookID, String libraryID, String customerID);
	String buyBook(String isbn, String bookstoreID, String customerID);

	LogInResponse logIn(String email, String password) throws DatabaseConnection.LoginException;
}

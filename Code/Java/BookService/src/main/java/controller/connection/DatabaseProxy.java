package controller.connection;

import model.Book;
import model.DetailedBook;

import java.util.List;

public interface DatabaseProxy {
	String getBookDetails(String isbn);
	public List<Book> search(String searchTerm) throws DatabaseConnection.ServerOfflineException, DatabaseConnection.SearchException;
	public List<Book> advancedSearch(String title, String author, int year, String isbn, Book.Category category) throws DatabaseConnection.ServerOfflineException, DatabaseConnection.SearchException;

}

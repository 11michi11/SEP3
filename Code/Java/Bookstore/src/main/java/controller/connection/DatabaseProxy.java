package controller.connection;


import java.util.List;

import model.Book;

public interface DatabaseProxy {

	List<Book> search(String searchTerm) throws DatabaseConnection.ServerOfflineException, DatabaseConnection.SearchException;
	List<Book> advancedSearch(String title, String author, int year, String isbn, Book.Category category) throws DatabaseConnection.ServerOfflineException, DatabaseConnection.SearchException;
	String addBook(Book book);
	String deleteBook(String isbn);
}

package controller.connection;


import java.util.List;

import model.Book;

public interface DatabaseProxy {
	String getBookDetails(String isbn);
	public List<Book> search(String searchTerm) throws DatabaseConnection.ServerOfflineException, DatabaseConnection.SearchException;
	public List<Book> advancedSearch(String title, String author, int year, String isbn, Book.Category category) throws DatabaseConnection.ServerOfflineException, DatabaseConnection.SearchException;
	public Book addBook(Book book);
	Book deleteBook(String isbn);
}

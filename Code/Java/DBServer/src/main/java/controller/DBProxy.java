package controller;

import model.*;

import java.util.List;

public interface DBProxy {

    DetailedBook getBookDetails(String isbn);

    List<Book> getAllBooks();

    Book getBookByLibraryBookId(String bookid) throws HibernateAdapter.BookNotFoundException;

    List<Book> advancedSearch(String isbn, String title, String author, int year, Book.Category category);

    void addBookToLibrary(LibraryStorage libraryBook);

    void addBookToBookStore(BookStoreStorage bookStoreBook);

    void deleteBookFromLibrary(LibraryStorage libraryBook);

    void deleteBookFromBookStore(BookStoreStorage bookStoreBook);

    Book getBookByIsbn(String isbn) throws HibernateAdapter.BookNotFoundException;

    List<Customer> getAllCustomers();

    void addCustomer(Customer customer) throws HibernateAdapter.CustomerEmailException;
}

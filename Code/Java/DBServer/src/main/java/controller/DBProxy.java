package controller;

import model.*;

import java.util.List;

public interface DBProxy {

    List<Book> advancedSearch(String isbn, String title, String author, int year, Book.Category category);

    @SuppressWarnings("unchecked")
    List<Book> advancedSearchInLibrary(String libraryId, String isbn, String title, String author, int year, Book.Category category);

    @SuppressWarnings("unchecked")
    List<Book> advancedSearchInBookStore(String bookStoreId, String isbn, String title, String author, int year, Book.Category category);

    DetailedBook getBookDetails(String isbn);

    Book getBookByLibraryBookId(String bookid) throws HibernateAdapter.BookNotFoundException;

    Book getBookByIsbn(String isbn) throws HibernateAdapter.BookNotFoundException;

    void addBookToLibrary(LibraryStorage libraryBook);

    void addBookToBookStore(BookStoreStorage bookStoreBook);

    void deleteBookFromLibrary(LibraryStorage libraryBook);

    void deleteBookFromBookStore(BookStoreStorage bookStoreBook);

    void addCustomer(Customer customer) throws HibernateAdapter.CustomerEmailException;
}

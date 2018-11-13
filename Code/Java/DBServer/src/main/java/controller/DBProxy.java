package controller;

import model.Book;
import model.BookStoreStorage;
import model.DetailedBook;
import model.LibraryStorage;

import java.util.List;

public interface DBProxy {

    DetailedBook getBookDetails(String isbn);

    List<Book> getAllBooks();

    List<Book> advancedSearch(String isbn, String title, String author, int year, Book.Category category);

    void addBookToLibrary(LibraryStorage libraryBook);

    void addBookToBookStore(BookStoreStorage bookStoreBook);

    void deleteBookFromLibrary(LibraryStorage libraryBook);

    void deleteBookFromBookStore(BookStoreStorage bookStoreBook);

    Book getBookByIsbn(String isbn) throws HibernateAdapter.BookNotFoundException;

}

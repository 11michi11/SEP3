package controller;

import controller.repositories.*;
import model.*;

import java.util.List;

public interface DBProxy {

    List<Book> search(String searchTerm);

    //
    List<Book> advancedSearch(String isbn, String title, String author, int year, Book.Category category);
//
    @SuppressWarnings("unchecked")
    List<Book> advancedSearchInLibrary(String libraryId, String isbn, String title, String author, int year, Book.Category category);
//
    @SuppressWarnings("unchecked")
    List<Book> advancedSearchInBookStore(String bookStoreId, String isbn, String title, String author, int year, Book.Category category);
//
    DetailedBook getBookDetails(String isbn) throws  BookRepository.BookNotFoundException;
//
    Book getBookByLibraryBookId(String bookid) throws  BookRepository.BookNotFoundException;
//
    Book getBookByIsbn(String isbn) throws BookRepository.BookNotFoundException;

    @SuppressWarnings("unchecked")
    List<LibraryStorage> getLibrariesStorageByIsbnAndLibrary(String isbn, String libraryid);

    void addBookToLibrary(Book book, String libraryid) throws LibraryRepository.LibraryNotFoundException;

    void addBookToBookStore(Book book, String bookStoreId) throws BookStoreRepository.BookStoreNotFoundException, BookStoreStorageRepository.BookAlreadyInBookStoreException;

    void deleteBookFromLibrary(String bookId, String libraryId) throws BookRepository.BookNotFoundException, LibraryRepository.LibraryNotFoundException, LibraryStorageRepository.BookAlreadyDeletedException;

    void deleteBookFromBookStore(String isbn, String bookStoreId) throws BookRepository.BookNotFoundException, BookStoreRepository.BookStoreNotFoundException;

    void addCustomer(Customer customer) throws CustomerRepository.CustomerEmailException;

    void borrowBook(String isbn,String libraryID, String customerID);
}

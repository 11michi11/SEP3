package controller;

import controller.repositories.*;
import model.*;

import java.util.List;

public interface DBProxy {

    List<Book> search(String searchTerm);

    //
    List<Book> advancedSearch(String isbn, String title, String author, int year, Book.Category category);

    List<Book> searchInLibrary(String searchTerm, String libraryId);

    //
    @SuppressWarnings("unchecked")
    List<Book> advancedSearchInLibrary(String libraryId, String isbn, String title, String author, int year, Book.Category category);

    List<Book> searchInBookStore(String searchTerm, String bookStoreId);

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

    void deleteBookFromLibrary(String bookid) throws BookRepository.BookNotFoundException, LibraryRepository.LibraryNotFoundException, LibraryStorageRepository.BookAlreadyDeletedException, LibraryStorageRepository.LibraryStorageNotFoundException;

    void deleteBookFromBookStore(String isbn, String bookstoreId) throws BookRepository.BookNotFoundException, BookStoreRepository.BookStoreNotFoundException, BookStoreStorageRepository.BookStoreStorageNotFoundException;

    void addCustomer(Customer customer) throws CustomerRepository.CustomerEmailException;

    void borrowBook(String isbn,String libraryID, String customerID) throws LibraryStorageRepository.LibraryStorageNotFoundException, CustomerRepository.CustomerNotFoundException, LibraryRepository.LibraryNotFoundException;

	void buyBook(String isbn, String bookstoreId, String customerId) throws BookStoreStorageRepository.BookStoreStorageNotFoundException, CustomerRepository.CustomerNotFoundException, BookStoreRepository.BookStoreNotFoundException;

	void addLibraryAdministrator(String libraryID, String name, String email, String password) throws LibraryRepository.LibraryNotFoundException;

	void addBookStoreAdministrator(String bookstoreID, String name, String email, String password) throws BookStoreRepository.BookStoreNotFoundException;

    void deleteBookStoreAdministrator(String adminID) throws BookStoreRepository.BookStoreNotFoundException, BookStoreAdminRepository.BookStoreAdminNotFoundException;

    void deleteLibraryAdministrator(String adminID) throws LibraryAdminRepository.LibraryAdminNotFoundException;

    User getUserByEmail(String email) throws RepositoryManager.UserNotFoundException;

    void confirmBookstoreOrder(String orderId) throws CustomerRepository.CustomerNotFoundException;

	void returnBook(String orderId) throws LibraryStorageRepository.LibraryStorageNotFoundException, CustomerRepository.CustomerNotFoundException;

    List<LibraryOrder> getLibraryOrders(String libraryId);

    List<BookStoreOrder> getBookStoreOrders(String bookStoreId);
}

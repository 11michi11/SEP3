package controller.repositories;

import controller.DBProxy;
import model.*;

import java.util.List;

public class RepositoryManager implements DBProxy {

    private BookRepo bookRepo;
    private LibraryRepo libraryRepo;
    private BookStoreRepo bookStoreRepo;
    private LibraryStorageRepo libraryStorageRepo;
    private BookStoreStorageRepo bookStoreStorageRepo;
    private BookStorageRepo bookStorageRepo;
    private CustomerRepo custormerRepo;

    private static RepositoryManager ourInstance = new RepositoryManager();

    public static RepositoryManager getInstance() {
        return ourInstance;
    }

    private RepositoryManager() {
        bookRepo = BookRepository.getInstance();
        libraryRepo = LibraryRepository.getInstance();
        bookStoreRepo = BookStoreRepository.getInstance();
        libraryStorageRepo = LibraryStorageRepository.getInstance();
        bookStoreStorageRepo = BookStoreStorageRepository.getInstance();
        bookStorageRepo = BookStorageRepository.getInstance();
        custormerRepo = CustomerRepository.getInstance();
    }

    @Override
    public List<Book> advancedSearch(String isbn, String title, String author, int year, Book.Category category) {
        return bookRepo.advancedSearch(isbn, title, author, year, category);
    }

    @Override
    public List<Book> advancedSearchInLibrary(String libraryId, String isbn, String title, String author, int year, Book.Category category) {
        return libraryStorageRepo.advancedSearch(libraryId, isbn, title, author, year, category);
    }

    @Override
    public List<Book> advancedSearchInBookStore(String bookStoreId, String isbn, String title, String author, int year, Book.Category category) {
        return bookStoreStorageRepo.advancedSearch(bookStoreId, isbn, title, author, year, category);
    }

    @Override
    public DetailedBook getBookDetails(String isbn) throws BookRepository.BookNotFoundException {
        return bookStorageRepo.getBookDetails(isbn);
    }

    @Override
    public Book getBookByLibraryBookId(String bookid) throws BookRepository.BookNotFoundException {
        return libraryStorageRepo.getBookByBookId(bookid);
    }

    @Override
    public Book getBookByIsbn(String isbn) throws BookRepository.BookNotFoundException {
        return bookRepo.get(isbn);
    }

    @Override
    public List<LibraryStorage> getLibrariesStorageByIsbnAndLibrary(String isbn, String libraryid) {
        return libraryStorageRepo.getStoragesByIsbnAndLibrary(isbn, libraryid);
    }

    @Override
    public void addBookToLibrary(Book book, String libraryid) throws LibraryRepository.LibraryNotFoundException {
        libraryStorageRepo.addBookToLibrary(book, libraryid);
    }

    @Override
    public void addBookToBookStore(Book book, String bookStoreId) throws BookStoreRepository.BookStoreNotFoundException {
        bookStoreStorageRepo.addBookToBookStore(book, bookStoreId);
    }

    @Override
    public void deleteBookFromLibrary(String bookId, String libraryId) throws BookRepository.BookNotFoundException, LibraryRepository.LibraryNotFoundException {
        libraryStorageRepo.deleteBookFromLibrary(bookId,libraryId);
    }

    @Override
    public void deleteBookFromBookStore(String isbn, String bookStoreId) throws BookRepository.BookNotFoundException, BookStoreRepository.BookStoreNotFoundException {
        bookStoreStorageRepo.deleteBookFromBookStore(isbn,bookStoreId);
    }

    @Override
    public void addCustomer(Customer customer) throws CustomerRepository.CustomerEmailException {
        custormerRepo.add(customer);
    }
}

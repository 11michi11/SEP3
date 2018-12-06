package controller.repositories;

import controller.DBProxy;
import model.*;

import java.util.List;
import java.util.UUID;

public class RepositoryManager implements DBProxy {

    private BookRepo bookRepo;
    private LibraryRepo libraryRepo;
    private BookStoreRepo bookStoreRepo;
    private LibraryStorageRepo libraryStorageRepo;
    private BookStoreStorageRepo bookStoreStorageRepo;
    private BookStorageRepo bookStorageRepo;
    private CustomerRepo customerRepo;
    private LibraryOrderRepo libraryOrderRepo;
    private BookStoreOrderRepo bookStoreOrderRepo;
    private LibraryAdminRepo libraryAdminRepo;
    private BookStoreAdminRepo bookStoreAdminRepo;

    private static RepositoryManager ourInstance = new RepositoryManager();

    public static DBProxy getInstance() {
        return ourInstance;
    }

    private RepositoryManager() {
        bookRepo = BookRepository.getInstance();
        libraryRepo = LibraryRepository.getInstance();
        bookStoreRepo = BookStoreRepository.getInstance();
        libraryStorageRepo = LibraryStorageRepository.getInstance();
        bookStoreStorageRepo = BookStoreStorageRepository.getInstance();
        bookStorageRepo = BookStorageRepository.getInstance();
        customerRepo = CustomerRepository.getInstance();
        libraryOrderRepo = LibraryOrderRepository.getInstance();
        bookStoreOrderRepo = BookStoreOrderRepository.getInstance();
        libraryAdminRepo = LibraryAdminRepository.getInstance();
        bookStoreAdminRepo = BookStoreAdminRepository.getInstance();

    }

    @Override
    public List<Book> search(String searchTerm){
        return bookRepo.search(searchTerm);
    }

    @Override
    public List<Book> advancedSearch(String isbn, String title, String author, int year, Book.Category category) {
        return bookRepo.advancedSearch(isbn, title, author, year, category);
    }

    @Override
    public List<Book> searchInLibrary(String searchTerm, String libraryId) {
        return libraryStorageRepo.search(searchTerm, libraryId);
    }

    @Override
    public List<Book> advancedSearchInLibrary(String libraryId, String isbn, String title, String author, int year, Book.Category category) {
        return libraryStorageRepo.advancedSearch(libraryId, isbn, title, author, year, category);
    }

    @Override
    public List<Book> searchInBookStore(String searchTerm, String bookStoreId) {
        return bookStoreStorageRepo.search(searchTerm, bookStoreId);
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
    public void addBookToBookStore(Book book, String bookStoreId) throws BookStoreRepository.BookStoreNotFoundException, BookStoreStorageRepository.BookAlreadyInBookStoreException {
        bookStoreStorageRepo.addBookToBookStore(book, bookStoreId);
    }

    @Override
    public void deleteBookFromLibrary(String bookId) throws BookRepository.BookNotFoundException, LibraryRepository.LibraryNotFoundException, LibraryStorageRepository.BookAlreadyDeletedException, LibraryStorageRepository.LibraryStorageNotFoundException {
        libraryStorageRepo.deleteBookFromLibrary(bookId);
    }

    @Override
    public void deleteBookFromBookStore(String isbn, String bookstoreid) throws BookRepository.BookNotFoundException, BookStoreRepository.BookStoreNotFoundException, BookStoreStorageRepository.BookStoreStorageNotFoundException {
        bookStoreStorageRepo.deleteBookFromBookStore(isbn, bookstoreid);
    }

    @Override
    public void addCustomer(Customer customer) throws CustomerRepository.CustomerEmailException {
        customerRepo.add(customer);
    }

    @Override
    public void borrowBook(String isbn, String libraryID, String customerID) throws LibraryStorageRepository.LibraryStorageNotFoundException, CustomerRepository.CustomerNotFoundException, LibraryRepository.LibraryNotFoundException {
        List<String> ids = libraryStorageRepo.getAvailableBooks(isbn,libraryID);
        libraryOrderRepo.add(ids.get(0),customerID);
    }

    @Override
    public void buyBook(String isbn, String bookstoreId, String customerId) throws BookStoreStorageRepository.BookStoreStorageNotFoundException, CustomerRepository.CustomerNotFoundException, BookStoreRepository.BookStoreNotFoundException {
        bookStoreOrderRepo.add(isbn, bookstoreId, customerId);
    }

    @Override
    public void addLibraryAdministrator(String libraryID, String name, String email, String password) throws LibraryRepository.LibraryNotFoundException {
        Library library = libraryRepo.get(libraryID);
        String id = UUID.randomUUID().toString();
        LibraryAdmin admin = new LibraryAdmin(id, library, name, email, password);
        libraryAdminRepo.add(admin);
    }

    @Override
    public void addBookStoreAdministrator(String bookstoreID, String name, String email, String password) throws BookStoreRepository.BookStoreNotFoundException {
        BookStore bookStore = bookStoreRepo.get(bookstoreID);
        String id = UUID.randomUUID().toString();
        BookStoreAdmin admin = new BookStoreAdmin(id, bookStore, name, email, password);
        bookStoreAdminRepo.add(admin);
    }

    @Override
    public void deleteBookStoreAdministrator(String adminID) throws BookStoreAdminRepository.BookStoreAdminNotFoundException {
        BookStoreAdmin admin = bookStoreAdminRepo.getBookstoreAdmin(adminID);
        bookStoreAdminRepo.delete(admin);
    }

    @Override
    public void deleteLibraryAdministrator(String adminID) throws LibraryAdminRepository.LibraryAdminNotFoundException {
        LibraryAdmin admin = libraryAdminRepo.getLibraryAdmin(adminID);
        libraryAdminRepo.delete(admin);
    }

    @Override
    public void confirmBookstoreOrder(String orderId) throws CustomerRepository.CustomerNotFoundException {
        bookStoreOrderRepo.delete(orderId);
    }


}

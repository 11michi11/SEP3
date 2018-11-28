package controller.repositories;

import model.Book;
import model.BookStoreStorage;

import java.util.List;

public interface BookStoreStorageRepo {

    String addBookToBookStore(Book book, String bookStoreId) throws BookStoreRepository.BookStoreNotFoundException, BookStoreStorageRepository.BookAlreadyInBookStoreException;

    void deleteBookFromBookStore(String isbn, String bookstoreid) throws BookRepository.BookNotFoundException, BookStoreRepository.BookStoreNotFoundException, BookStoreStorageRepository.BookStoreStorageNotFoundException;

    List<BookStoreStorage> getStoragesByIsbnAndBookstore(String isbn, String bookstoreId);

    List<Book> search(String searchTerm, String bookstoreId);

    List<Book> advancedSearch(String bookStoreId, String isbn, String title, String author, int year, Book.Category category);

    List<BookStoreStorage> getBookStoresStorageByIsbn(String isbn);

    BookStoreStorage getStorageByBookId(String isbn) throws BookStoreStorageRepository.BookStoreStorageNotFoundException;
}

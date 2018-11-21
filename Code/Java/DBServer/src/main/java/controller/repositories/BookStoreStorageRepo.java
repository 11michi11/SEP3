package controller.repositories;

import model.Book;
import model.BookStoreStorage;

import java.util.List;

public interface BookStoreStorageRepo {

    void addBookToBookStore(Book book, String bookStoreId) throws BookStoreRepository.BookStoreNotFoundException;

    void deleteBookFromBookStore(String isbn, String bookStoreId) throws BookRepository.BookNotFoundException, BookStoreRepository.BookStoreNotFoundException;

    List<Book> search(String searchTerm);

    List<Book> advancedSearch(String bookStoreId, String isbn, String title, String author, int year, Book.Category category);

    List<BookStoreStorage> getBookStoresStorageByIsbn(String isbn);
}

package controller.repositories;

import model.Book;

import java.util.List;

public interface BookStoreStorageRepo {

    void addBookToBookStore(Book book, String bookStoreId);

    void deleteBookFromBookStore(String isbn, String bookStoreId);

    List<Book> search(String searchTerm);

    List<Book> advancedSearch(String bookStoreId, String isbn, String title, String author, int year, Book.Category category);

}

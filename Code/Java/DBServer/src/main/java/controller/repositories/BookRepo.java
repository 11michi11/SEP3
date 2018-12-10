package controller.repositories;

import model.Book;

import java.util.List;

public interface BookRepo {

    void add(Book book);

    void delete(String isbn) throws BookRepository.BookNotFoundException;

    Book get(String isbn) throws BookRepository.BookNotFoundException;

    void saveOrUpdate(Book book);

    List<Book> search(String searchTerm);

    List<Book> advancedSearch(String isbn, String title, String author, int year, Book.Category category);

}

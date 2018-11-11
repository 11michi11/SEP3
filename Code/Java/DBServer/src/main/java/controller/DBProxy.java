package controller;

import model.Book;
import model.DetailedBook;

import java.util.List;

public interface DBProxy {

    DetailedBook getBookDetails(String isbn);

    public List<Book> getAllBooks();
    public List<Book> advancedSearch(String isbn, String title, String author, int year, Book.Category category);
 }

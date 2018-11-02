package controller;

import model.Book;

import java.util.List;

public interface DBProxy {

    public List<Book> getAllBooks();
    public List<Book> search(String searchTerm);
    public List<Book> advancedSearch(String isbn, String title, String author, int year, Book.Category category);
 }

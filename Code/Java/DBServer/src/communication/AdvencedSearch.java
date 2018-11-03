package communication;

import model.Book;

import java.util.HashMap;
import java.util.Map;

public class AdvencedSearch implements RequestArgs {

    private String isbn;
    private String title;
    private String author;
    private int year;
    private Book.Category category;


    public AdvencedSearch(String isbn, String title, String author, int year, Book.Category category) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.year = year;
        this.category = category;
    }

    @Override
    public Map<String, Object> getArguments() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("isbn", isbn);
        arguments.put("title", title);
        arguments.put("author", author);
        arguments.put("year", year);
        arguments.put("category", category);
        return arguments;
    }
}

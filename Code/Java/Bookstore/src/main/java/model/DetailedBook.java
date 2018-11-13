package model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DetailedBook {

    private static final String KEY = "Institutions";

    private Book book;
    private Map<String, List<Institution>> institutions = new HashMap<>();



    public DetailedBook(Book book, List<Library> libraries, List<BookStore> bookStores){
        institutions.put(KEY,new LinkedList<>());
        this.book = book;
        libraries.forEach(library -> institutions.get(KEY).add(library));
        bookStores.forEach(bookStore-> institutions.get(KEY).add(bookStore));

    }

    @Override
    public String toString() {
        return "DetailedBook{" +
                "book=" + book +
                ", institutions=" + institutions +
                '}';
    }
}
package model;

import com.google.gson.Gson;

import java.util.*;

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

    public String toJSON(){
        Gson gson = new Gson();
        StringBuilder sb = new StringBuilder("{");

        sb.append("\"book\":");
        sb.append(gson.toJson(book));
        sb.append(",\"institutions\":");
        sb.append(gson.toJson(institutions.get(KEY)));
        sb.append("}");

        return  sb.toString();
        //{"book": ?bookjson?,"institutions":[{"libraryID":"libid"},{"bookstoreid":"bid"}]}
    }

    public static void main(String[] args) {
        Book book = new Book("isbn", "title", "author", 0, Book.Category.Science);
        List<Institution> institutions = new LinkedList<>(Arrays.asList(new Library("libid"),new BookStore("bid")));
        DetailedBook detailedBook = new DetailedBook(book, Collections.singletonList(new Library("libid")), Collections.singletonList(new BookStore("bid")));

        Gson gson = new Gson();
        String json = detailedBook.toJSON();
        System.out.println(json);

    }
}

package model;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class DetailedBook {

    private static final String KEY = "Institutions";

    private Book book;
    private List<LibraryStorage> libraryStorages;
    private List<BookStore> bookStores;


    public DetailedBook(Book book, List<LibraryStorage> libraries, List<BookStore> bookStores) {
        this.book = book;
        libraryStorages = libraries;
        this.bookStores = bookStores;
    }

    @Override
    public String toString() {
        return "DetailedBook{" +
                "book=" + book +
                ", libraryStorages=" + libraryStorages +
                ", bookStores=" + bookStores +
                '}';
    }

    public JsonElement toJSON() {
        Gson gson = new Gson();
        StringBuilder sb = new StringBuilder("{");

        sb.append("\"book\":");
        sb.append(gson.toJson(book));
        sb.append(",\"libraries\":");

        Map<String, List<LibraryStorage>> listMap = libraryStorages.stream()
                .collect(groupingBy(libraryStorages -> libraryStorages.getLibrary().getLibraryID()));

        List<LibraryBook> libraries = new LinkedList<>();

        listMap.forEach((key, value) -> {
            int quantity = (int) value.stream().filter(LibraryStorage::isAvailable).count();
            libraries.add(new LibraryBook(key,value.get(0).getLibrary().getName(), quantity));
        });

        sb.append(gson.toJson(libraries));

        sb.append(",\"bookstores\":");
        sb.append(gson.toJson(bookStores));
        sb.append("}");

        return new JsonParser().parse(sb.toString());
        //{"book":{"isbn":"978-83-246-7758-0","title":"Core Java","author":"Cay S. Horstmann, Gary Cornell","year":2014,"category":"Science"},"libraries":[{"libraryid":"ce78ef57-77ec-4bb7-82a2-1a78d3789aef","bookid":"978-83-246-7758-0","available":true},{"libraryid":"ce78ef57-77ec-4bb7-82a2-1a78d3789aef","bookid":"978-83-246-7758-0","available":true}],"bookstores":[{"bookstoreid":"eb3777c8-77fe-4acd-962d-6853da2e05e0"}]}
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DetailedBook that = (DetailedBook) o;

        if (book != null ? !book.equals(that.book) : that.book != null) return false;
        if (libraryStorages != null ? !libraryStorages.equals(that.libraryStorages) : that.libraryStorages != null)
            return false;
        return bookStores != null ? bookStores.equals(that.bookStores) : that.bookStores == null;
    }

    @Override
    public int hashCode() {
        int result = book != null ? book.hashCode() : 0;
        result = 31 * result + (libraryStorages != null ? libraryStorages.hashCode() : 0);
        result = 31 * result + (bookStores != null ? bookStores.hashCode() : 0);
        return result;
    }

    private class LibraryBook {

        private final String libraryName;
        public String libraryid;
        public int quantity;

        private LibraryBook(String libraryid, String libraryName, int quantity) {
            this.libraryid = libraryid;
            this.libraryName = libraryName;
            this.quantity = quantity;
        }
    }
}

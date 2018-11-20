package model;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LibraryBook {

    private Book book;
    private Map<String, Boolean> libraryBooks;

    public LibraryBook(Book book, Map<String, Boolean> libraryBooks) {
        this.book = book;
        this.libraryBooks = libraryBooks;
    }

    public LibraryBook(Book book) {
        this.book = book;
    }

    public void loadLibraryBooksFromStorages(List<LibraryStorage> storages) {
        Map<String, Boolean> map = storages.stream()
                .collect(Collectors.toMap(storage -> storage.getId().getBookid(),
                        LibraryStorage::isAvailable));

        this.libraryBooks = map;
    }

    @Override
    public String toString() {
        return "LibraryBook{" +
                "book=" + book +
                ", libraryBooks=" + libraryBooks +
                '}';
    }

    public JsonElement toJson() {
        Gson gson = new Gson();
        StringBuilder sb = new StringBuilder("{");

        sb.append("\"book\":").append(gson.toJson(book));

        sb.append(",\"libraryBooks\":[");

        libraryBooks.forEach((bookid, availability) ->
                sb.append("{\"bookid\":\"").append(bookid)
                        .append("\",\"availability\":").append(availability)
                        .append("},"));

        sb.delete(sb.length() - 1, sb.length());
        sb.append("]}");
        return new JsonParser().parse(sb.toString());
    }

    public Book getBook() {
        return book;
    }
}

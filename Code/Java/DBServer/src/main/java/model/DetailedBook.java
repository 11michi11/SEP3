package model;

import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;

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

    public String toJSON() {
        Gson gson = new Gson();
        StringBuilder sb = new StringBuilder("{");

        sb.append("\"book\":");
        sb.append(gson.toJson(book));
        sb.append(",\"libraries\":");

        List<LibraryBook> libraries = libraryStorages.stream()
                .map(libraryStorage -> new LibraryBook(libraryStorage.getId().getLibrary().getLibraryID(), libraryStorage.getId().getBook().getIsbn(), libraryStorage.isAvailable()))
                .collect(Collectors.toList());
        sb.append(gson.toJson(libraries));

        sb.append(",\"bookstores\":");
        sb.append(gson.toJson(bookStores));
        sb.append("}");

        return sb.toString();
        //{"book":{"isbn":"978-83-246-7758-0","title":"Core Java","author":"Cay S. Horstmann, Gary Cornell","year":2014,"category":"Science"},"libraries":[{"libraryid":"ce78ef57-77ec-4bb7-82a2-1a78d3789aef","bookid":"978-83-246-7758-0","available":true},{"libraryid":"ce78ef57-77ec-4bb7-82a2-1a78d3789aef","bookid":"978-83-246-7758-0","available":true}],"bookstores":[{"bookstoreid":"eb3777c8-77fe-4acd-962d-6853da2e05e0"}]}
    }

    public static void main(String[] args) {
        Book book = new Book("isbn", "title", "author", 0, Book.Category.Science);
        Library lib = new Library("libid");
        LibraryStorageID id = new LibraryStorageID();
        id.setBook(book);
        id.setLibrary(lib);
        id.setBookid("bookid");
        LibraryStorage libs = new LibraryStorage();
        libs.setId(id);
        libs.setAvailable(true);

        DetailedBook detailedBook = new DetailedBook(book,Collections.singletonList(libs) , Collections.singletonList(new BookStore("bid")));

        Gson gson = new Gson();
        String json = detailedBook.toJSON();
        System.out.println(json);

    }

    private class LibraryBook {

        public String libraryid;
        public String bookid;
        public boolean available;

        private LibraryBook(String libraryid, String bookid, boolean available) {
            this.libraryid = libraryid;
            this.bookid = bookid;
            this.available = available;
        }
    }
}

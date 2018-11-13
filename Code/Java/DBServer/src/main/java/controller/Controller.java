package controller;

import com.google.gson.internal.LinkedTreeMap;
import communication.DBServer;
import communication.Request;
import communication.Response;
import model.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

public class Controller {

    private DBProxy db;
    private DBServer server;

    private Controller(DBProxy db) {
        this.db = db;
        this.server = new DBServer(this);
        server.start();
    }


    public static void main(String[] args) {
        DBProxy db = new HibernateAdapter();
        Controller controller = new Controller(db);
    }

    public List<Book> getAllBooks() {
        return db.getAllBooks();
    }

    public List<Book> search(String searchTerm) {
        final String emptyStringValue = "!@#$%^&*()"; //this value represents empty string for query so that it is not matched to any typical string value
        if (searchTerm.equals(""))
            searchTerm = emptyStringValue;

        int year;
        try {
            year = Integer.parseInt(searchTerm);
        } catch (NumberFormatException e) {
            year = 0;
        }

        String cat = searchTerm.toLowerCase();
        cat = cat.substring(0, 1).toUpperCase() + cat.substring(1);
        Book.Category searchCategory;
        try {
            searchCategory = Book.Category.valueOf(cat);
        } catch (IllegalArgumentException e) {
            searchCategory = Book.Category.Empty;
        }

        return db.advancedSearch(searchTerm, searchTerm, searchTerm, year, searchCategory);
    }

    public List<Book> advancedSearch(String isbn, String title, String author, int year, Book.Category category) {
        final String emptyStringValue = "!@#$%^&*()"; //this value represents empty string for query so that it is not matched to any typical string value
        if (isbn.equals(""))
            isbn = emptyStringValue;

        if (title.equals(""))
            title = emptyStringValue;

        if (author.equals(""))
            author = emptyStringValue;

        return db.advancedSearch(isbn, title, author, year, category);
    }

    public String handleRequest(String json) {
        try {
            Request request = Request.fromJson(json);

            switch (request.getOperation()) {
                case AdvancedSearch:
                    return handleAdvancedSearch(request);
                case Search:
                    return handleSearch(request);
                case BookDetails:
                    return handleBookDetails(request);
                case AddBook:
                    return handleAddBook(request);
                case DeleteBook:
                    return handleDeleteBook(request);
            }
            throw new InvalidOperationException("Wrong operation");
        } catch (Request.RequestJsonFormatException | InvalidOperationException | HibernateAdapter.BookNotFoundException e) {
            //send error
            return new Response(Response.Status.Error, e.getMessage()).toJson();
        }
    }

    private String handleAddBook(Request request) {
        Map<String, Object> arguments = request.getArguments();
        Book book = parseLinkedTreeMapToBook((LinkedTreeMap<String, Object>) arguments.get("book"));

        boolean isLibrary = (boolean) arguments.get("library");
        String institutionId = (String) arguments.get("id");
        if (isLibrary) {
            Library lib = new Library(institutionId);
            String bookid = UUID.randomUUID().toString();
            LibraryStorageID libId = new LibraryStorageID(book, lib, bookid);
            LibraryStorage libraryStorage = new LibraryStorage(libId, true);

            db.addBookToLibrary(libraryStorage);
        } else {
            BookStore bookStore = new BookStore(institutionId);
            BookStoreStorageID bookStoreId = new BookStoreStorageID(book, bookStore);
            BookStoreStorage bookStoreStorage = new BookStoreStorage(bookStoreId);

            db.addBookToBookStore(bookStoreStorage);
        }
        return new Response(Response.Status.OK, "Added").toJson();
    }

    private Book parseLinkedTreeMapToBook(LinkedTreeMap<String, Object> map){
        String isbn = (String) map.get("isbn");
        String title = (String) map.get("title");
        String author = (String) map.get("author");
        int year = ((Double) map.get("year")).intValue();
        Book.Category category = Book.Category.valueOf((String) map.get("category"));
        return new Book(isbn, title, author, year, category);
    }

    private String handleDeleteBook(Request request) throws HibernateAdapter.BookNotFoundException {
        Map<String, Object> arguments = request.getArguments();

        boolean isLibrary = (boolean) arguments.get("library");
        String institutionId = (String) arguments.get("id");
        if (isLibrary) {
            Book book = db.getBookByLibraryBookId((String) arguments.get("bookid"));
            Library lib = new Library(institutionId);
            String bookid = (String) arguments.get("bookid");
            LibraryStorageID libId = new LibraryStorageID(book, lib, bookid);
            LibraryStorage libraryStorage = new LibraryStorage(libId, true);

            db.deleteBookFromLibrary(libraryStorage);
        } else {
            String isbn = (String) arguments.get("isbn");
            Book book = db.getBookByIsbn(isbn);
            BookStore bookStore = new BookStore(institutionId);
            BookStoreStorageID bookStoreId = new BookStoreStorageID(book, bookStore);
            BookStoreStorage bookStoreStorage = new BookStoreStorage(bookStoreId);

            db.deleteBookFromBookStore(bookStoreStorage);
        }
        return new Response(Response.Status.OK, "Deleted").toJson();
    }

    private String handleBookDetails(Request request) {
        Map<String, Object> arguments = request.getArguments();
        String isbn = (String) arguments.get("isbn");
        DetailedBook book = db.getBookDetails(isbn);

        return new Response(Response.Status.OK, book.toJSON()).toJson();//.replace("\\", "");
    }

    private String handleSearch(Request request) {
        Map<String, Object> arguments = request.getArguments();
        String searchTerm = (String) arguments.get("searchTerm");

        List<Book> books = search(searchTerm);

        return new Response(Response.Status.OK, books).toJson();
    }

    private String handleAdvancedSearch(Request request) {
        Map<String, Object> arguments = request.getArguments();
        String isbn = (String) arguments.get("isbn");
        String title = (String) arguments.get("title");
        String author = (String) arguments.get("author");
        int year = ((Double) arguments.get("year")).intValue();
        Book.Category category = Book.Category.valueOf((String) arguments.get("category"));

        List<Book> books = advancedSearch(isbn, title, author, year, category);

        return new Response(Response.Status.OK, books).toJson();
    }

    private class InvalidOperationException extends Exception {
        public InvalidOperationException(String msg) {
            super(msg);
        }
    }
}

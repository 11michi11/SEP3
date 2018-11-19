package controller;

import com.google.gson.internal.LinkedTreeMap;
import communication.DBServer;
import communication.Request;
import communication.Response;
import model.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Controller {

    private DBProxy db;
    private DBServer server;

    protected Controller(DBProxy db, DBServer server) {
        this.db = db;
        this.server = server;
        server.setController(this);
        server.start();
    }

    public static void main(String[] args) {
        DBProxy db = new HibernateAdapter();
        DBServer server = new DBServer();
        Controller controller = new Controller(db, server);
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
                case RegisterCustomer:
                    return handleRegisterCustomer(request);
                case LibraryAdvancedSearch:
                    return handleLibraryAdvancedSearch(request);
                case BookStoreAdvancedSearch:
                    return handleBookStoreAdvancedSearch(request);
                case LibrarySearch:
                    return handleLibrarySearch(request);
                case BookStoreSearch:
                    return handleBookStoreSearch(request);
            }
            throw new InvalidOperationException("Wrong operation");
        } catch (Request.RequestJsonFormatException | InvalidOperationException | HibernateAdapter.BookNotFoundException e) {
            //send error
            return new Response(Response.Status.Error, e.getMessage()).toJson();
        }
    }

    public String handleSearch(Request request) {
        Map<String, Object> arguments = request.getArguments();
        String searchTerm = (String) arguments.get("searchTerm");

        List<Book> books = search(searchTerm);

        return new Response(Response.Status.OK, books).toJson();
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

    public List<Book> searchLibrary(String searchTerm, String libraryId) {
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

        return db.advancedSearchInLibrary(libraryId, searchTerm, searchTerm, searchTerm, year, searchCategory);
    }

    public List<Book> searchBookStore(String searchTerm, String bookStoreId) {
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

        return db.advancedSearchInBookStore(bookStoreId, searchTerm, searchTerm, searchTerm, year, searchCategory);
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

    public String handleLibrarySearch(Request request){
        Map<String, Object> arguments = request.getArguments();
        String searchTerm = (String) arguments.get("searchTerm");
        String libraryId = (String) arguments.get("libraryid");


        List<Book> books = searchLibrary(searchTerm, libraryId);

        return new Response(Response.Status.OK, books).toJson();
    }

    public String handleBookStoreSearch(Request request){
        Map<String, Object> arguments = request.getArguments();
        String searchTerm = (String) arguments.get("searchTerm");
        String bookStoreId = (String) arguments.get("bookstoreid");


        List<Book> books = searchBookStore(searchTerm,bookStoreId);

        return new Response(Response.Status.OK, books).toJson();
    }

    public String handleLibraryAdvancedSearch(Request request){
        Map<String, Object> arguments = request.getArguments();
        String isbn = (String) arguments.get("isbn");
        String title = (String) arguments.get("title");
        String author = (String) arguments.get("author");
        int year = ((Double) arguments.get("year")).intValue();
        Book.Category category = Book.Category.valueOf((String) arguments.get("category"));

        String libraryid = (String) arguments.get("libraryid");

        List<Book> books = db.advancedSearchInLibrary(libraryid, isbn, title, author, year, category);

        return new Response(Response.Status.OK, books).toJson();
    }

    public String handleBookStoreAdvancedSearch(Request request){
        Map<String, Object> arguments = request.getArguments();
        String isbn = (String) arguments.get("isbn");
        String title = (String) arguments.get("title");
        String author = (String) arguments.get("author");
        int year = ((Double) arguments.get("year")).intValue();
        Book.Category category = Book.Category.valueOf((String) arguments.get("category"));

        String bookstoreid = (String) arguments.get("bookstoreid");

        List<Book> books = db.advancedSearchInLibrary(bookstoreid, isbn, title, author, year, category);

        return new Response(Response.Status.OK, books).toJson();
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

    private Book parseLinkedTreeMapToBook(LinkedTreeMap<String, Object> map){
        String isbn = (String) map.get("isbn");
        String title = (String) map.get("title");
        String author = (String) map.get("author");
        int year = ((Double) map.get("year")).intValue();
        Book.Category category = Book.Category.valueOf((String) map.get("category"));
        return new Book(isbn, title, author, year, category);
    }

    private String handleBookDetails(Request request) {
        Map<String, Object> arguments = request.getArguments();
        String isbn = (String) arguments.get("isbn");
        DetailedBook book = db.getBookDetails(isbn);

        return new Response(Response.Status.OK, book.toJSON()).toJson();//.replace("\\", "");
    }

    private String handleRegisterCustomer(Request request) {
        Customer customer = createCustomerFromArguments(request.getArguments());

        try {
            db.addCustomer(customer);
            return new Response(Response.Status.OK,"Customer created" ).toJson();
        } catch (HibernateAdapter.CustomerEmailException e) {
            return new Response(Response.Status.Error,e.getMessage()).toJson();
        }
    }

    private Customer createCustomerFromArguments(Map<String, Object> args){
        String name = (String) args.get("name");
        String email = (String) args.get("email");
        String address = (String) args.get("address");
        int phoneNum = ((Double) args.get("phoneNum")).intValue();

        return new Customer(UUID.randomUUID().toString(), name, email, address, phoneNum);
    }

    private class InvalidOperationException extends Exception {
        InvalidOperationException(String msg) {
            super(msg);
        }
    }
}

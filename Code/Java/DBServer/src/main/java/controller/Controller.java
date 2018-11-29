package controller;

import com.google.gson.internal.LinkedTreeMap;
import communication.DBServer;
import communication.Request;
import communication.Response;
import controller.repositories.*;
import model.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Controller {

    private DBProxy db;
    private DBServer server;

    private Controller(DBProxy db, DBServer server) {
        this.db = db;
        this.server = server;
        server.setController(this);
        server.start();
    }

    public static void main(String[] args) {
        DBProxy db = RepositoryManager.getInstance();
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
                case LibraryBookDetails:
                    return handleLibraryBookDetails(request);
                case MakeLibraryOrder:
                    return handleMakeLibraryOrder(request);
	            case MakeBookStoreOrder:
	            	return handleMakeBookstoreOrder(request);
            }
            throw new InvalidOperationException("Wrong operation");
        } catch (Request.RequestJsonFormatException | InvalidOperationException | BookRepository.BookNotFoundException | LibraryRepository.LibraryNotFoundException | BookStoreRepository.BookStoreNotFoundException | BookStoreStorageRepository.BookAlreadyInBookStoreException | LibraryStorageRepository.BookAlreadyDeletedException | LibraryStorageRepository.LibraryStorageNotFoundException | BookStoreStorageRepository.BookStoreStorageNotFoundException | CustomerRepository.CustomerNotFoundException e) {
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
        return db.search(searchTerm);
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

        return db.searchInLibrary(searchTerm, libraryId);
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

    public String handleLibrarySearch(Request request) {
        Map<String, Object> arguments = request.getArguments();
        String searchTerm = (String) arguments.get("searchTerm");
        String libraryId = (String) arguments.get("libraryid");


        List<Book> books = searchLibrary(searchTerm, libraryId);

        return new Response(Response.Status.OK, books).toJson();
    }

    public String handleBookStoreSearch(Request request) {
        Map<String, Object> arguments = request.getArguments();
        String searchTerm = (String) arguments.get("searchTerm");
        String bookStoreId = (String) arguments.get("bookstoreid");


        List<Book> books = searchBookStore(searchTerm, bookStoreId);

        return new Response(Response.Status.OK, books).toJson();
    }

    public String handleLibraryAdvancedSearch(Request request) {
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

    public String handleBookStoreAdvancedSearch(Request request) {
        Map<String, Object> arguments = request.getArguments();
        String isbn = (String) arguments.get("isbn");
        String title = (String) arguments.get("title");
        String author = (String) arguments.get("author");
        int year = ((Double) arguments.get("year")).intValue();
        Book.Category category = Book.Category.valueOf((String) arguments.get("category"));

        String bookstoreid = (String) arguments.get("bookstoreid");

        final String emptyStringValue = "!@#$%^&*()"; //this value represents empty string for query so that it is not matched to any typical string value
        if (isbn.equals(""))
            isbn = emptyStringValue;

        if (title.equals(""))
            title = emptyStringValue;

        if (author.equals(""))
            author = emptyStringValue;


        List<Book> books = db.advancedSearchInBookStore(bookstoreid, isbn, title, author, year, category);

        return new Response(Response.Status.OK, books).toJson();
    }

    private String handleAddBook(Request request) throws LibraryRepository.LibraryNotFoundException, BookStoreRepository.BookStoreNotFoundException, BookStoreStorageRepository.BookAlreadyInBookStoreException {
        Map<String, Object> arguments = request.getArguments();
        Book book = parseLinkedTreeMapToBook((LinkedTreeMap<String, Object>) arguments.get("book"));

        boolean isLibrary = (boolean) arguments.get("library");
        String institutionId = (String) arguments.get("id");
        if (isLibrary) {
            db.addBookToLibrary(book, institutionId);
        } else {
            db.addBookToBookStore(book, institutionId);
        }
        return new Response(Response.Status.OK, "Added").toJson();
    }

    private String handleDeleteBook(Request request) throws BookRepository.BookNotFoundException, LibraryRepository.LibraryNotFoundException, BookStoreRepository.BookStoreNotFoundException, LibraryStorageRepository.BookAlreadyDeletedException, LibraryStorageRepository.LibraryStorageNotFoundException, BookStoreStorageRepository.BookStoreStorageNotFoundException {
        Map<String, Object> arguments = request.getArguments();

        boolean isLibrary = (boolean) arguments.get("library");
        String institutionId = (String) arguments.get("id");
        if (isLibrary) {
            String bookId = (String) arguments.get("bookid");
            db.deleteBookFromLibrary(bookId);
        } else {
            String isbn = (String) arguments.get("isbn");
            db.deleteBookFromBookStore(isbn, institutionId);
        }
        return new Response(Response.Status.OK, "Deleted").toJson();
    }

    private Book parseLinkedTreeMapToBook(LinkedTreeMap<String, Object> map) {
        String isbn = (String) map.get("isbn");
        String title = (String) map.get("title");
        String author = (String) map.get("author");
        int year = ((Double) map.get("year")).intValue();
        Book.Category category = Book.Category.valueOf((String) map.get("category"));
        return new Book(isbn, title, author, year, category);
    }

    private String handleBookDetails(Request request) throws BookRepository.BookNotFoundException {
        Map<String, Object> arguments = request.getArguments();
        String isbn = (String) arguments.get("isbn");
        DetailedBook book = db.getBookDetails(isbn);

        return new Response(Response.Status.OK, book.toJSON()).toJson();
    }

    private String handleLibraryBookDetails(Request request) throws BookRepository.BookNotFoundException {
        Map<String, Object> arguments = request.getArguments();
        String isbn = (String) arguments.get("isbn");
        String libraryid = (String) arguments.get("libraryid");

        List<LibraryStorage> storage = db.getLibrariesStorageByIsbnAndLibrary(isbn, libraryid);

        try {
            Book book = storage.get(0).getBook();

            LibraryBook libraryBook = new LibraryBook(book);
            libraryBook.loadLibraryBooksFromStorages(storage);
            return new Response(Response.Status.OK, libraryBook.toJson()).toJson();
        } catch (IndexOutOfBoundsException e) {
            throw new BookRepository.BookNotFoundException("There is no book with isbn: " + isbn);
        }
    }

    private String handleMakeLibraryOrder(Request request) throws LibraryStorageRepository.LibraryStorageNotFoundException, LibraryRepository.LibraryNotFoundException, CustomerRepository.CustomerNotFoundException {
        Map<String, Object> arguments = request.getArguments();
        String isbn = (String) arguments.get("isbn");
        String libraryId = (String) arguments.get("libraryId");
        String customerId = (String) arguments.get("customerId");

        db.borrowBook(isbn, libraryId, customerId);
        return new Response(Response.Status.OK, "Added").toJson();
    }

	private String handleMakeBookstoreOrder(Request request) throws BookStoreStorageRepository.BookStoreStorageNotFoundException, BookStoreRepository.BookStoreNotFoundException, CustomerRepository.CustomerNotFoundException {
		Map<String, Object> arguments = request.getArguments();
        String isbn = (String) arguments.get("isbn");
        String bookstoreId = (String) arguments.get("bookstoreId");
        String customerId = (String) arguments.get("customerId");

		db.buyBook(isbn, bookstoreId, customerId);
		return new Response(Response.Status.OK, "Added").toJson();

	}

    private String handleRegisterCustomer(Request request) {
        Customer customer = createCustomerFromArguments(request.getArguments());

        try {
            db.addCustomer(customer);
            return new Response(Response.Status.OK, "Customer created").toJson();
        } catch (CustomerRepository.CustomerEmailException e) {
            return new Response(Response.Status.Error, e.getMessage()).toJson();
        }
    }

    private Customer createCustomerFromArguments(Map<String, Object> args) {
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

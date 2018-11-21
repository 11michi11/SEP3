import communication.DBServer;
import communication.Request;
import controller.Controller;
import controller.DBProxy;
import controller.repositories.LibraryRepository;
import model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

public class ControllerTest {

    @Mock
    private DBProxy db;
    @Mock
    private DBServer server;

    @InjectMocks
    private Controller controller;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void searchByTitleTest() {
        Book book = new Book("isbn", "title", "author", 0, Book.Category.Fantasy);
        List<Book> books = Collections.singletonList(book);
        when(db.advancedSearch("Tolkien", "Tolkien", "Tolkien", 0, Book.Category.Empty)).thenReturn(books);

        Map<String, Object> args = new HashMap<>();
        args.put("searchTerm", "Tolkien");
        Request request = new Request(Request.Operation.Search, args);

        assertEquals(books, controller.search("Tolkien"));
    }

    @Test
    public void searchByYearTest() {
        Book book = new Book("isbn", "title", "author", 1974, Book.Category.Fantasy);
        List<Book> books = Collections.singletonList(book);
        when(db.advancedSearch("1974", "1974", "1974", 1974, Book.Category.Empty)).thenReturn(books);

        Map<String, Object> args = new HashMap<>();
        args.put("searchTerm", 1974);
        Request request = new Request(Request.Operation.Search, args);

        assertEquals(books, controller.search("1974"));
    }

    @Test
    public void searchByCategoryTest() {
        Book book = new Book("isbn", "title", "author", 1974, Book.Category.Fantasy);
        List<Book> books = Collections.singletonList(book);
        when(db.advancedSearch("Fantasy", "Fantasy", "Fantasy", 0, Book.Category.Fantasy)).thenReturn(books);

        Map<String, Object> args = new HashMap<>();
        args.put("searchTerm", "Fantasy");
        Request request = new Request(Request.Operation.Search, args);

        assertEquals(books, controller.search("Fantasy"));
    }

    @Test
    public void searchByEmptyStringTest() {
        Book book = new Book("isbn", "title", "author", 1974, Book.Category.Fantasy);
        List<Book> books = Collections.singletonList(book);
        when(db.advancedSearch("!@#$%^&*()", "!@#$%^&*()", "!@#$%^&*()", 0, Book.Category.Empty)).thenReturn(books);

        Map<String, Object> args = new HashMap<>();
        args.put("searchTerm", "");
        Request request = new Request(Request.Operation.Search, args);

        assertEquals(books, controller.search(""));
    }

    @Test
    public void addBookToLibraryTest() {
        Book book = new Book("isbn", "title", "author", 1974, Book.Category.Fantasy);
        String institutionId = "id";
        try {
            db.addBookToLibrary(book, institutionId);
        } catch (LibraryRepository.LibraryNotFoundException e) {
            fail(e.getMessage());
        }

        //Request request = new Request(Request.Operation.AddBook, );

    }

}

import communication.DBServer;
import communication.Request;
import controller.Controller;
import controller.DBProxy;
import model.Book;
import org.junit.jupiter.api.Assertions;
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


}

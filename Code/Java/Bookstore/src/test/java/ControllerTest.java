import controller.Controller;
import controller.connection.DatabaseProxy;
import model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;


public class ControllerTest {

    @Mock
    private DatabaseProxy db;

    //Pass mocks to injectmocks constructor
    @InjectMocks
    private Controller controller;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void searchTest(){
        Book book = new Book("978-83-8116-1", "Lord of the Rings: Fellowship of the Ring", "J.R.R Tolkien",1954 , Book.Category.Fantasy );
        List<Book> books = Collections.singletonList(book);
        when(db.search("Tolkien")).thenReturn(books);

        assertEquals(books,controller.search("Tolkien"));
    }

    @Test
    public void advancedSearchTest() {
        Book book = new Book("978-83-8116-1", "Lord of the Rings: Fellowship of the Ring", "J.R.R Tolkien",1954 , Book.Category.Fantasy );
        List<Book> books = Collections.singletonList(book);
        when(db.advancedSearch("Lord of the Rings", "Tolkien", 1954, "", Book.Category.Fantasy)).thenReturn(books);

        assertEquals(books, controller.advancedSearch("Lord of the Rings", "Tolkien", 1954, "", Book.Category.Fantasy));

    }

    @Test
    public void addBook() {
        Book book = new Book("978-83-8116-1", "Lord of the Rings: Fellowship of the Ring", "J.R.R Tolkien",1954 , Book.Category.Fantasy );
        when(db.addBook(book)).thenReturn("{\"status\":\"OK\",\"content\":\"Added\"}");
        assertNotNull(controller.addBook(book));
    }

    @Test
    public void deleteBook() {
        Book book = new Book("978-83-8116-1", "Lord of the Rings: Fellowship of the Ring", "J.R.R Tolkien",1954 , Book.Category.Fantasy );
        db.addBook(book);
        String json = "{\"status\":\"OK\",\"content\":\"Deleted\"}";
        when(db.deleteBook("978-83-8116-1")).thenReturn("{\"status\":\"OK\",\"content\":\"Deleted\"}");
        assertEquals(json,controller.deleteBook(book.getIsbn()));

    }
    @Test
    public void detialBook() {
        Book book = new Book("978-83-8116-1", "Lord of the Rings: Fellowship of the Ring", "J.R.R Tolkien",1954 , Book.Category.Fantasy );
        db.addBook(book);
        String json = "{\"book\":{\"isbn\":\"978-83-8116-1\",\"title\":\"Lord of the Rings: Fellowship of the Ring\",\"author\":\"J.R.R Tolkien\",\"year\":1954,\"category\":\"Fantasy\"},\"libraries\":[{\"libraryid\":\"ce78ef57-77ec-4bb7-82a2-1a78d3789aef\",\"bookid\":\"978-83-246-7758-0\",\"available\":true},{\"libraryid\":\"ce78ef57-77ec-4bb7-82a2-1a78d3789aef\",\"bookid\":\"978-83-246-7758-0\",\"available\":true}],\"bookstores\":[{\"bookstoreid\":\"eb3777c8-77fe-4acd-962d-6853da2e05e0\"}]}";
        when(db.getBookDetails("978-83-8116-1")).thenReturn("{\"book\":{\"isbn\":\"978-83-8116-1\",\"title\":\"Lord of the Rings: Fellowship of the Ring\",\"author\":\"J.R.R Tolkien\",\"year\":1954,\"category\":\"Fantasy\"},\"libraries\":[{\"libraryid\":\"ce78ef57-77ec-4bb7-82a2-1a78d3789aef\",\"bookid\":\"978-83-246-7758-0\",\"available\":true},{\"libraryid\":\"ce78ef57-77ec-4bb7-82a2-1a78d3789aef\",\"bookid\":\"978-83-246-7758-0\",\"available\":true}],\"bookstores\":[{\"bookstoreid\":\"eb3777c8-77fe-4acd-962d-6853da2e05e0\"}]}");
        assertEquals(json,controller.getBookDetails(book.getIsbn()));

    }

}

import controller.connection.DatabaseConnection;
import model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class DatabaseConnectionTest {

    private DatabaseConnection db;

    @BeforeEach
    void setup(){
        db = new DatabaseConnection();
    }

    @Test
    void searchTest(){

        List<Book> java = db.search("java");

        System.out.println(java);
    }

    @Test
    void advancedSearchTest(){

        List<Book> java = db.advancedSearch("java", "author", 0, "isbn"    , Book.Category.Science);

        System.out.println(java);
    }

    @Test
    void addBookTest(){
        Book book = new Book("newIsbn", "newTitle", "newAuthor", 0, Book.Category.Children);

        String java = db.addBook(book);

        System.out.println(java);
    }

    @Test
    void deleteBookTest(){
        Book book = new Book("newIsbn", "newTitle", "newAuthor", 0, Book.Category.Children);
        String java = db.deleteBook("newIsbn");

        System.out.println(java);
    }




}

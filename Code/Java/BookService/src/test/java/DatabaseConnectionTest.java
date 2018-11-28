import controller.connection.DatabaseConnection;
import model.Book;
import org.junit.BeforeClass;
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
    void bookDetailsTest(){

        String java = db.getBookDetails("978-83-246-7758-0");

        System.out.println(java);
    }

    @Test
    void borrowBookTest(){

        String java = db.borrowBook("978-83-246-7758-0", "ce78ef57-77ec-4bb7-82a2-1a78d3789aef", "0227f11c-8f66-4835-8283-021f0df8b558");

        System.out.println(java);
    }

    @Test
    void buyBookTest(){

        String java = db.buyBook("978-83-246-7758-0", "eb3777c8-77fe-4acd-962d-6853da2e05e0", "0227f11c-8f66-4835-8283-021f0df8b558");

        System.out.println(java);
    }




}

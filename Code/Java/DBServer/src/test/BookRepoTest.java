import controller.repositories.BookRepo;
import controller.repositories.BookRepository;
import model.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BookRepoTest {

    private BookRepo bookRepo;

    @BeforeEach
    void setup(){
        this.bookRepo = BookRepository.getInstance();
    }

    @Test
    public void getByIsbnTest(){
        Book book = new Book("978-83-8116-1", "Lord of the Rings: Fellowship of the Ring", "J.R.R Tolkien", 1954, Book.Category.Fantasy);

        try {
            assertEquals(book, bookRepo.get("978-83-8116-1"));
        } catch (BookRepository.BookNotFoundException e) {
            Assertions.fail(e.getMessage());
        }
    }


    @Test
    public void getByIsbnExceptionTest(){
        assertThrows(BookRepository.BookNotFoundException.class, () -> bookRepo.get("doesnotexsit"));
    }

    @Test
    public void serachTest(){
        Book book = new Book("978-83-8116-1", "Lord of the Rings: Fellowship of the Ring", "J.R.R Tolkien", 1954, Book.Category.Fantasy);

        List<Book> books = Collections.singletonList(book);

        assertEquals(books, bookRepo.search("Tolkien"));
        assertEquals(books, bookRepo.search("Lord"));
        assertEquals(books, bookRepo.search("lord"));
        assertEquals(books, bookRepo.search("1954"));
        assertEquals(books, bookRepo.search("Fantasy"));

    }

    @Test
    public void advancedSearchTest(){

    }



}

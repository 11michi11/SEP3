import controller.repositories.*;
import model.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class LibraryStorageRepoTest {

    private LibraryStorageRepo libraryStorageRepo;
    private BookRepo bookRepo;

    @BeforeEach
    void setup() {
        libraryStorageRepo = LibraryStorageRepository.getInstance();
        bookRepo = BookRepository.getInstance();
    }

    @Test
    void addBookToLibraryTest() {
        Book book = new Book("testisbn", "testtitle", "testauthor", 999, Book.Category.Poetry);

        try {
            String bookid = libraryStorageRepo.addBookToLibrary(book, "ce78ef57-77ec-4bb7-82a2-1a78d3789aef");

            //Clean up
            libraryStorageRepo.deleteBookFromLibrary(bookid, "ce78ef57-77ec-4bb7-82a2-1a78d3789aef");
            bookRepo.delete("testisbn");
        } catch (LibraryRepository.LibraryNotFoundException e) {
            fail("No library");
        } catch (BookRepository.BookNotFoundException e) {
            fail("Book not created");
        }
    }

    @Test
    void addBookToLibraryWhenBookAlreadyExistsTest() {
        Book book = new Book("testisbn", "testtitle", "testauthor", 999, Book.Category.Poetry);
        bookRepo.add(book);

        try {
            String bookid = libraryStorageRepo.addBookToLibrary(book, "ce78ef57-77ec-4bb7-82a2-1a78d3789aef");
            //Clean up
            libraryStorageRepo.deleteBookFromLibrary(bookid, "ce78ef57-77ec-4bb7-82a2-1a78d3789aef");
            bookRepo.delete("testisbn");
        } catch (LibraryRepository.LibraryNotFoundException e) {
            fail("No library");
        } catch (BookRepository.BookNotFoundException e) {
            fail("Book not created");
        }
    }

    @Test
    void getBookByIdTest(){
        Book book = new Book("testisbn", "testtitle", "testauthor", 999, Book.Category.Poetry);
        bookRepo.add(book);

        try {
            String bookid = libraryStorageRepo.addBookToLibrary(book, "ce78ef57-77ec-4bb7-82a2-1a78d3789aef");
            //set up end

            assertEquals(book, libraryStorageRepo.getBookByBookId(bookid));


            //Clean up
            libraryStorageRepo.deleteBookFromLibrary(bookid, "ce78ef57-77ec-4bb7-82a2-1a78d3789aef");
            bookRepo.delete("testisbn");
        } catch (LibraryRepository.LibraryNotFoundException e) {
            fail("No library");
        } catch (BookRepository.BookNotFoundException e) {
            fail("Book not created");
        }
    }

    @Test
    void searchTest(){

    }

}

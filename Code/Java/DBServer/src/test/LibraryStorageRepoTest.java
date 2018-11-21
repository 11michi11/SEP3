import controller.repositories.*;
import model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class LibraryStorageRepoTest {

    private LibraryStorageRepo libraryStorageRepo;
    private BookRepo bookRepo;

    private static final String LIBRARY_ID = "ce78ef57-77ec-4bb7-82a2-1a78d3789aef";

    @BeforeEach
    void setup() {
        libraryStorageRepo = LibraryStorageRepository.getInstance();
        bookRepo = BookRepository.getInstance();
    }

    @Test
    void addBookToLibraryTest() {
        Book book = new Book("testisbn", "testtitle", "testauthor", 999, Book.Category.Poetry);

        try {
            String bookid = libraryStorageRepo.addBookToLibrary(book, LIBRARY_ID);

            //Clean up
            libraryStorageRepo.deleteBookFromLibrary(bookid, LIBRARY_ID);
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
            String bookid = libraryStorageRepo.addBookToLibrary(book, LIBRARY_ID);
            //Clean up
            libraryStorageRepo.deleteBookFromLibrary(bookid, LIBRARY_ID);
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
            String bookid = libraryStorageRepo.addBookToLibrary(book, LIBRARY_ID);
            //set up end

            assertEquals(book, libraryStorageRepo.getBookByBookId(bookid));


            //Clean up
            libraryStorageRepo.deleteBookFromLibrary(bookid, LIBRARY_ID);
            bookRepo.delete("testisbn");
        } catch (LibraryRepository.LibraryNotFoundException e) {
            fail("No library");
        } catch (BookRepository.BookNotFoundException e) {
            fail("Book not created");
        }
    }

    @Test
    void searchTest(){
        Book book = new Book("testisbn", "testtitle", "testauthor", 999, Book.Category.Poetry);
        List<Book> books = Collections.singletonList(book);
        bookRepo.add(book);
        String bookid = null;
        try {
            bookid = libraryStorageRepo.addBookToLibrary(book, LIBRARY_ID);
        } catch (LibraryRepository.LibraryNotFoundException e) {
            fail("Adding to library failed");
        }

        assertEquals(books , libraryStorageRepo.search(LIBRARY_ID, "testisbn"));
        assertEquals(books , libraryStorageRepo.search(LIBRARY_ID, "testtitle"));
        assertEquals(books , libraryStorageRepo.search(LIBRARY_ID, "testauthor"));
        assertEquals(books , libraryStorageRepo.search(LIBRARY_ID, "999"));
        assertEquals(books , libraryStorageRepo.search(LIBRARY_ID, "Poetry"));

        try {
            libraryStorageRepo.deleteBookFromLibrary(bookid, LIBRARY_ID);
            bookRepo.delete("testisbn");
        } catch (BookRepository.BookNotFoundException | LibraryRepository.LibraryNotFoundException e) {
            fail("Deleting failed, really bad");
        }
    }

    @Test
    void advancedSearchTest(){
        Book book = new Book("testisbn", "testtitle", "testauthor", 999, Book.Category.Poetry);
        List<Book> books = Collections.singletonList(book);
        bookRepo.add(book);
        String bookid = null;
        try {
            bookid = libraryStorageRepo.addBookToLibrary(book, LIBRARY_ID);
        } catch (LibraryRepository.LibraryNotFoundException e) {
            fail("Adding to library failed");
        }

        assertEquals(books , libraryStorageRepo.advancedSearch(LIBRARY_ID, "testisbn", "testtitle", "testauthor", 999, Book.Category.Poetry));

        try {
            libraryStorageRepo.deleteBookFromLibrary(bookid, LIBRARY_ID);
            bookRepo.delete("testisbn");
        } catch (BookRepository.BookNotFoundException | LibraryRepository.LibraryNotFoundException e) {
            fail("Deleting failed, really bad");
        }
    }


}

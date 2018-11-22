import controller.repositories.*;
import model.Book;
import model.Library;
import model.LibraryStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
            try {
                libraryStorageRepo.deleteBookFromLibrary(bookid, LIBRARY_ID);
            } catch (LibraryStorageRepository.BookAlreadyDeletedException bookAlreadyDeletedException) {
                fail("Should not throw");
            }
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
        } catch (LibraryStorageRepository.BookAlreadyDeletedException e) {
            fail("Should not throw");
        }
    }

    @Test
    void doubleDeleteBookFromLibrary() {
        Book book = new Book("testisbn", "testtitle", "testauthor", 999, Book.Category.Poetry);
        bookRepo.add(book);

        try {
            String bookid = libraryStorageRepo.addBookToLibrary(book, LIBRARY_ID);
            //set up end

            try {
                libraryStorageRepo.deleteBookFromLibrary(bookid, LIBRARY_ID);
            } catch (LibraryStorageRepository.BookAlreadyDeletedException e) {
                fail("Should not throw");
            }

            assertThrows(LibraryStorageRepository.BookAlreadyDeletedException.class, () -> libraryStorageRepo.deleteBookFromLibrary(bookid, LIBRARY_ID));

            bookRepo.delete("testisbn");
        } catch (LibraryRepository.LibraryNotFoundException e) {
            fail("No library");
        } catch (BookRepository.BookNotFoundException e) {
            fail("Book not created");
        }
    }

    @Test
    void getBookByIdTest() {
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
        } catch (LibraryStorageRepository.BookAlreadyDeletedException e) {
            fail("Should not thorw");
        }
    }

    @Test
    void searchTest() {
        Book book = new Book("testisbn", "testtitle", "testauthor", 999, Book.Category.Poetry);
        List<Book> books = Collections.singletonList(book);
        bookRepo.add(book);
        String bookid = null;
        try {
            bookid = libraryStorageRepo.addBookToLibrary(book, LIBRARY_ID);
        } catch (LibraryRepository.LibraryNotFoundException e) {
            fail("Adding to library failed");
        }

        assertEquals(books, libraryStorageRepo.search(LIBRARY_ID, "testisbn"));
        assertEquals(books, libraryStorageRepo.search(LIBRARY_ID, "testtitle"));
        assertEquals(books, libraryStorageRepo.search(LIBRARY_ID, "testauthor"));
        assertEquals(books, libraryStorageRepo.search(LIBRARY_ID, "999"));
        assertEquals(books, libraryStorageRepo.search(LIBRARY_ID, "Poetry"));

        try {
            libraryStorageRepo.deleteBookFromLibrary(bookid, LIBRARY_ID);
            bookRepo.delete("testisbn");
        } catch (BookRepository.BookNotFoundException | LibraryRepository.LibraryNotFoundException e) {
            fail("Deleting failed, really bad");
        } catch (LibraryStorageRepository.BookAlreadyDeletedException e) {
            fail("Should not throw");
        }
    }

    @Test
    void advancedSearchTest() {
        Book book = new Book("testisbn", "testtitle", "testauthor", 999, Book.Category.Poetry);
        List<Book> books = Collections.singletonList(book);
        bookRepo.add(book);
        String bookid = null;
        try {
            bookid = libraryStorageRepo.addBookToLibrary(book, LIBRARY_ID);
        } catch (LibraryRepository.LibraryNotFoundException e) {
            fail("Adding to library failed");
        }

        assertEquals(books, libraryStorageRepo.advancedSearch(LIBRARY_ID, "testisbn", "testtitle", "testauthor", 999, Book.Category.Poetry));

        try {
            libraryStorageRepo.deleteBookFromLibrary(bookid, LIBRARY_ID);
            bookRepo.delete("testisbn");
        } catch (BookRepository.BookNotFoundException | LibraryRepository.LibraryNotFoundException e) {
            fail("Deleting failed, really bad");
        } catch (LibraryStorageRepository.BookAlreadyDeletedException e) {
            fail("Should not throw");
        }
    }

    @Test
    public void getIdsOfAvalaibleBooks() {

        String bookid = "961af2c5-a57b-4855-bf40-d6d64fbd5f96";
        List<String> ids= Collections.singletonList(bookid);
        assertEquals(ids,libraryStorageRepo.getAvailableBooks("978-83-8116-1", LIBRARY_ID) );

    }

    @Test
    void getStorageByBookIdTest(){
        Book book = new Book("testisbn", "testtitle", "testauthor", 999, Book.Category.Poetry);
        List<Book> books = Collections.singletonList(book);
        bookRepo.add(book);
        String bookid = null;
        try {
            bookid = libraryStorageRepo.addBookToLibrary(book, LIBRARY_ID);
        } catch (LibraryRepository.LibraryNotFoundException e) {
            fail("Adding to library failed");
        }

        LibraryStorage libraryStorage = new LibraryStorage(bookid, new Library(LIBRARY_ID), book, true);


        try {
            assertEquals(libraryStorage,  libraryStorageRepo.getStorageByBookId(bookid)) ;
        } catch (LibraryStorageRepository.LibraryStorageNotFoundException e) {
            fail("No such library storage");
        }

        try {
            libraryStorageRepo.deleteBookFromLibrary(bookid, LIBRARY_ID);
            bookRepo.delete("testisbn");
        } catch (BookRepository.BookNotFoundException | LibraryRepository.LibraryNotFoundException e) {
            fail("Deleting failed, really bad");
        } catch (LibraryStorageRepository.BookAlreadyDeletedException e) {
            fail("Should not throw");
        }


    }



}

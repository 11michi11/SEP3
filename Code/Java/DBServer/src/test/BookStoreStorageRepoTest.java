import controller.repositories.*;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;


public class BookStoreStorageRepoTest {

    private BookRepo bookRepo;
    private BookStoreStorageRepo bookStoreStorageRepo;

    private static final String BOOKSTORE_ID = "eb3777c8-77fe-4acd-962d-6853da2e05e0";

    @BeforeEach
    public void setUp() {
        bookRepo = BookRepository.getInstance();
        bookStoreStorageRepo = BookStoreStorageRepository.getInstance();
    }

    @Test
    public void addBookToBookStore() {
        Book book = new Book("isbn", "title", "author", 0, Book.Category.Empty);

        try {
            String bookId = bookStoreStorageRepo.addBookToBookStore(book, BOOKSTORE_ID);

            bookStoreStorageRepo.deleteBookFromBookStore(bookId);
            bookRepo.delete("isbn");
        } catch (BookStoreRepository.BookStoreNotFoundException e) {
            fail("No Bookstore");
        } catch (BookRepository.BookNotFoundException e) {
            fail("There is not such a book");
        } catch (BookStoreStorageRepository.BookAlreadyInBookStoreException e) {
            fail("Should not throw");
        } catch (BookStoreStorageRepository.BookStoreStorageNotFoundException e) {
            fail("No bookstore storage");
        }
    }

    @Test
    public void addBookToBookStoreWhenBookAlreadyExistsTest() {
        Book book = new Book("isbn", "title", "author", 0, Book.Category.Empty);
        bookRepo.add(book);

        try {
            String bookId = bookStoreStorageRepo.addBookToBookStore(book, BOOKSTORE_ID);

            bookStoreStorageRepo.deleteBookFromBookStore(bookId);
            bookRepo.delete("isbn");
        } catch (BookStoreRepository.BookStoreNotFoundException e) {
            fail("No Bookstore");
        } catch (BookRepository.BookNotFoundException e) {
            e.printStackTrace();
            fail("There is not such a book");
        } catch (BookStoreStorageRepository.BookAlreadyInBookStoreException e) {
            fail("Should not throw");
        } catch (BookStoreStorageRepository.BookStoreStorageNotFoundException e) {
            fail("No bookstore storage");
        }
    }

    //	@Test
    public void getBookStoresStoragesByIdTest() {
        Book book = new Book("isbn", "title", "author", 0, Book.Category.Empty);
        bookRepo.add(book);


        try {
            String bookId = bookStoreStorageRepo.addBookToBookStore(book, BOOKSTORE_ID);
            //assertEquals(, bookStoreStorageRepo.getBookStoresStorageByIsbn("isbn"));

            bookStoreStorageRepo.deleteBookFromBookStore(bookId);
            bookRepo.delete("isbn");
        } catch (BookStoreRepository.BookStoreNotFoundException e) {
            fail("No Bookstore");
        } catch (BookRepository.BookNotFoundException e) {
            fail("There is no such a book");
        } catch (BookStoreStorageRepository.BookAlreadyInBookStoreException e) {
            fail("Should not throw");
        } catch (BookStoreStorageRepository.BookStoreStorageNotFoundException e) {
            fail("No bookstore storage");
        }


    }

    //	@Test
    public void search() {
        Book book = new Book("isbn", "title", "author", 0, Book.Category.Empty);
        List<Book> books = Collections.singletonList(book);
        bookRepo.add(book);
        String bookId = null;
        try {
            bookId = bookStoreStorageRepo.addBookToBookStore(book, BOOKSTORE_ID);

        } catch (BookStoreRepository.BookStoreNotFoundException e) {
            fail("No Bookstore");
        } catch (BookStoreStorageRepository.BookAlreadyInBookStoreException e) {
            fail("Should not throw");
        }
        assertEquals(books, bookStoreStorageRepo.search("isbn"));
        assertEquals(books, bookStoreStorageRepo.search("book"));
        assertEquals(books, bookStoreStorageRepo.search("author"));
        assertEquals(books, bookStoreStorageRepo.search("0"));
        assertEquals(books, bookStoreStorageRepo.search("Empty"));
        try {
            bookStoreStorageRepo.deleteBookFromBookStore(bookId);
            bookRepo.delete("isbn");
        } catch (BookRepository.BookNotFoundException e) {
            fail("No Bookstore");
        } catch (BookStoreRepository.BookStoreNotFoundException e) {
            fail("There is no such a book");
        } catch (BookStoreStorageRepository.BookStoreStorageNotFoundException e) {
            fail("No bookstore storage");
        }
    }

    @Test
    public void advancedSearch() {
        Book book = new Book("isbn", "title", "author", 0, Book.Category.Empty);
        List<Book> books = Collections.singletonList(book);
        bookRepo.add(book);

        String bookId = null;
        try {
            bookId = bookStoreStorageRepo.addBookToBookStore(book, BOOKSTORE_ID);

        } catch (BookStoreRepository.BookStoreNotFoundException e) {
            fail("No Bookstore");
        } catch (BookStoreStorageRepository.BookAlreadyInBookStoreException e) {
            fail("Should not throw");
        }
        assertEquals(books, bookStoreStorageRepo.advancedSearch("", "isbn", "title", "author", 0, Book.Category.Empty));
        try {
            bookStoreStorageRepo.deleteBookFromBookStore(bookId);
            bookRepo.delete("isbn");
        } catch (BookRepository.BookNotFoundException e) {
            fail("No Bookstore");
        } catch (BookStoreRepository.BookStoreNotFoundException e) {
            fail("There is no such a book");
        } catch (BookStoreStorageRepository.BookStoreStorageNotFoundException e) {
            fail("No bookstore storage");
        }
    }

    @Test
    void doubleAddBookToBookstoreExceptionTest() {
        Book book = new Book("isbn", "title", "author", 0, Book.Category.Empty);
        bookRepo.add(book);
        String bookId = null;
        try {
            try {
                bookId = bookStoreStorageRepo.addBookToBookStore(book, BOOKSTORE_ID);
            } catch (BookStoreStorageRepository.BookAlreadyInBookStoreException e) {
                fail("should not throw now");
            }

            assertThrows(BookStoreStorageRepository.BookAlreadyInBookStoreException.class, () -> bookStoreStorageRepo.addBookToBookStore(book, BOOKSTORE_ID));

            bookStoreStorageRepo.deleteBookFromBookStore(bookId);
            bookRepo.delete("isbn");
        } catch (BookStoreRepository.BookStoreNotFoundException e) {
            fail("No Bookstore");
        } catch (BookRepository.BookNotFoundException e) {
            e.printStackTrace();
            fail("There is not such a book");
        } catch (BookStoreStorageRepository.BookStoreStorageNotFoundException e) {
            fail("No bookstore storage");
        }
    }

    @Test
    void getStorageByIsbnTest() {
        Book book = new Book("testisbn", "testtitle", "testauthor", 999, Book.Category.Poetry);
        List<Book> books = Collections.singletonList(book);
        bookRepo.add(book);

        String bookId = null;
        try {
            bookId = bookStoreStorageRepo.addBookToBookStore(book, BOOKSTORE_ID);
        } catch (BookStoreRepository.BookStoreNotFoundException e) {
            fail("No bookstore");
        } catch (BookStoreStorageRepository.BookAlreadyInBookStoreException e) {
            fail("Book already there");
        }

        BookStoreStorage bookStoreStorage = new BookStoreStorage(bookId, new BookStore(BOOKSTORE_ID), book);


        try {
            assertEquals(bookStoreStorage, bookStoreStorageRepo.getStorageByBookId(bookId));
        } catch (BookStoreStorageRepository.BookStoreStorageNotFoundException e) {
            fail("No book store storage");
        }

        try {
            bookStoreStorageRepo.deleteBookFromBookStore(bookId);
            bookRepo.delete("testisbn");
        } catch (BookRepository.BookNotFoundException e) {
            fail("No book");
        } catch (BookStoreRepository.BookStoreNotFoundException e) {
            fail("No bookstore");
        } catch (BookStoreStorageRepository.BookStoreStorageNotFoundException e) {
            fail("No bookstore storage");
        }


    }


}

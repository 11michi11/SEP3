import controller.repositories.*;
import model.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

public class BookStoreOrderRepoTest {

    private BookStoreOrderRepo bookStoreOrderRepo;
    private BookStoreStorageRepo bookStoreStorageRepo;
    private CustomerRepo customerRepo;
    private BookRepo bookRepo;
    private String bookId;

    private static final String BOOK_STORE_ID = "eb3777c8-77fe-4acd-962d-6853da2e05e0";
    private static final String CUSTOMER_ID = "0227f11c-8f66-4835-8283-021f0df8b558";


    @BeforeEach
    void setup(){
        bookStoreOrderRepo = BookStoreOrderRepository.getInstance();
        bookStoreStorageRepo = BookStoreStorageRepository.getInstance();
        customerRepo = CustomerRepository.getInstance();
        bookRepo = BookRepository.getInstance();
        addBookToBookStore();
    }

    @Test
    void addTest(){
        try {
            String orderId = bookStoreOrderRepo.add("testisbn", BOOK_STORE_ID, CUSTOMER_ID);

            bookStoreOrderRepo.delete(orderId);
        } catch (CustomerRepository.CustomerNotFoundException e) {
            fail("No customer");
        }  catch (BookStoreStorageRepository.BookStoreStorageNotFoundException e) {
            fail("No bookstore storage");
        } catch (BookStoreRepository.BookStoreNotFoundException e) {
            fail("No book store");
        }
    }

    private void addBookToBookStore(){
        Book book = new Book("testisbn", "testtitle", "testauthor", 999, Book.Category.Poetry);
        List<Book> books = Collections.singletonList(book);
        bookRepo.add(book);
        try {
            bookId = bookStoreStorageRepo.addBookToBookStore(book, BOOK_STORE_ID);
        } catch (BookStoreStorageRepository.BookAlreadyInBookStoreException e) {
            fail("Book already there");
        } catch (BookStoreRepository.BookStoreNotFoundException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    private void cleanUp(){
        try {
            bookStoreStorageRepo.deleteBookFromBookStore("testisbn", BOOK_STORE_ID);
            bookRepo.delete("testisbn");
        }  catch (BookRepository.BookNotFoundException e) {
            fail("No book");
        }  catch (BookStoreRepository.BookStoreNotFoundException e) {
            fail("No bookstore");
        } catch (BookStoreStorageRepository.BookStoreStorageNotFoundException e) {
            fail("No bookstore storage");
        }
    }


}

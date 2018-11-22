import controller.repositories.*;
import model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.fail;

public class LibraryOrderRepoTest {

    private LibraryOrderRepo libraryOrderRepo;
    private LibraryStorageRepo libraryStorageRepo;
    private CustomerRepo customerRepo;
    private BookRepo bookRepo;

    private static final String LIBRARY_ID = "ce78ef57-77ec-4bb7-82a2-1a78d3789aef";
    private static final String CUSTOMER_ID = "0227f11c-8f66-4835-8283-021f0df8b558";
    private String bookId;

    @BeforeEach
    void setup(){
        libraryOrderRepo = LibraryOrderRepository.getInstance();
        libraryStorageRepo = LibraryStorageRepository.getInstance();
        customerRepo = CustomerRepository.getInstance();
        bookRepo = BookRepository.getInstance();
        bookId = addBookToLibrary();
    }

    @Test
    void addTest(){
        try {
            String orderId = libraryOrderRepo.add(bookId, CUSTOMER_ID);

            libraryOrderRepo.delete(orderId);
        } catch (LibraryStorageRepository.LibraryStorageNotFoundException e) {
            fail("No library storage");
        } catch (CustomerRepository.CustomerNotFoundException e) {
            fail("No customer");
        } catch (LibraryRepository.LibraryNotFoundException e) {
            fail("No library");
        }
    }


    private LibraryOrder createLibraryOrder(String bookId, String customerId) throws LibraryStorageRepository.LibraryStorageNotFoundException, CustomerRepository.CustomerNotFoundException {
        LibraryStorage libraryStorage = libraryStorageRepo.getStorageByBookId(bookId);
        Book book = libraryStorage.getBook();
        Library library = libraryStorage.getLibrary();
        Customer customer = customerRepo.get(customerId);
        Calendar dateOfOrder = Calendar.getInstance();
        Calendar returnDate = Calendar.getInstance();
        returnDate.add(Calendar.DAY_OF_MONTH, 30);

        String orderId = UUID.randomUUID().toString();
        return new LibraryOrder(orderId, libraryStorage, library, book, customer, dateOfOrder, returnDate);
    }

    private String addBookToLibrary(){
        Book book = new Book("testisbn", "testtitle", "testauthor", 999, Book.Category.Poetry);
        List<Book> books = Collections.singletonList(book);
        bookRepo.add(book);
        try {
            return libraryStorageRepo.addBookToLibrary(book, LIBRARY_ID);
        } catch (LibraryRepository.LibraryNotFoundException e) {
            fail("Adding to library failed");
        }
        return "";
    }

    @AfterEach
    private void cleanUp(){
        try {
            libraryStorageRepo.deleteBookFromLibrary(bookId, LIBRARY_ID);
            bookRepo.delete("testisbn");
        } catch (LibraryStorageRepository.BookAlreadyDeletedException bookAlreadyDeletedException) {
            fail("Should not throw");
        } catch (BookRepository.BookNotFoundException e) {
            fail("No book");
        } catch (LibraryRepository.LibraryNotFoundException e) {
            fail("No library");
        }
    }
}

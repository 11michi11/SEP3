import controller.repositories.*;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class BookStorageRepoTest {

    private BookStorageRepo bookStorageRepo;
    private BookRepo bookRepo;
    private LibraryStorageRepo libraryStorageRepo;
    private BookStoreStorageRepo bookStoreStorageRepo;

    private static final String LIBRARY_ID = "ce78ef57-77ec-4bb7-82a2-1a78d3789aef";
    private static final String BOOK_STORE_ID = "eb3777c8-77fe-4acd-962d-6853da2e05e0";

    @BeforeEach
    void setup(){
        this.bookStorageRepo = BookStorageRepository.getInstance();
        libraryStorageRepo = LibraryStorageRepository.getInstance();
        bookStoreStorageRepo = BookStoreStorageRepository.getInstance();
        bookRepo = BookRepository.getInstance();
    }

    @Test
    void getBookDetailsTest(){
        //set up
        Book book = new Book("testisbn", "testtitle", "testauthor", 999, Book.Category.Poetry);
        String bookid1 = null;
        String bookid2 = null;
        try {
            bookid1 = libraryStorageRepo.addBookToLibrary(book, LIBRARY_ID);
            bookid2 = libraryStorageRepo.addBookToLibrary(book, LIBRARY_ID);
            bookStoreStorageRepo.addBookToBookStore(book, BOOK_STORE_ID);
        } catch (LibraryRepository.LibraryNotFoundException | BookStoreRepository.BookStoreNotFoundException e) {
            fail("Adding failed, that's bad");
        } catch (BookStoreStorageRepository.BookAlreadyInBookStoreException e) {
            fail("Should not throw");
        }

        LibraryStorage libraryStorage1 = new LibraryStorage(bookid1, new Library(LIBRARY_ID), book , true);

        LibraryStorage libraryStorage2 = new LibraryStorage(bookid2, new Library(LIBRARY_ID), book , true);

        List<BookStore> bookStoreStorages = Collections.singletonList(new BookStore(BOOK_STORE_ID));
        List<LibraryStorage> libraryStorages = Arrays.asList(libraryStorage1, libraryStorage2);

        DetailedBook detailedBook = new DetailedBook(book, libraryStorages, bookStoreStorages);
        //set up end

        try {
            assertEquals(detailedBook ,bookStorageRepo.getBookDetails("testisbn") );
        } catch (BookRepository.BookNotFoundException e) {
            fail("Book not found");
        }


        try {
            bookStorageRepo.getBookDetails("testisbn");
        } catch (BookRepository.BookNotFoundException e) {
            fail("No isbn found");
        }

        try {
            //Clean up
            try {
                libraryStorageRepo.deleteBookFromLibrary(bookid1, LIBRARY_ID);
                libraryStorageRepo.deleteBookFromLibrary(bookid2, LIBRARY_ID);
            } catch (LibraryStorageRepository.BookAlreadyDeletedException bookAlreadyDeletedExcetion) {
                fail("Should not throw");
            }
            bookStoreStorageRepo.deleteBookFromBookStore("testisbn", BOOK_STORE_ID);
            bookRepo.delete("testisbn");
        } catch (LibraryRepository.LibraryNotFoundException e) {
            fail("No library");
        } catch (BookRepository.BookNotFoundException e) {
            fail("Book not created");
        } catch (BookStoreRepository.BookStoreNotFoundException e) {
            fail("No bookstore");
        }
    }




}

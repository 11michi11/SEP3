import controller.repositories.*;
import model.BookStore;
import model.Library;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class BookStoreRepoTest {

    private BookStoreRepo bookStoreRepo;

    @BeforeEach
    void setup(){
        bookStoreRepo = BookStoreRepository.getInstance();
    }

    @Test
    void getBookStoreByIdTest(){
        BookStore bookStore = new BookStore("eb3777c8-77fe-4acd-962d-6853da2e05e0");

        try {
            assertEquals(bookStore, bookStoreRepo.get("eb3777c8-77fe-4acd-962d-6853da2e05e0"));
        } catch (BookStoreRepository.BookStoreNotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void getLibraryByIdExceptionTest(){
        assertThrows(BookStoreRepository.BookStoreNotFoundException.class,() -> bookStoreRepo.get("doesnotexist"));
    }


}

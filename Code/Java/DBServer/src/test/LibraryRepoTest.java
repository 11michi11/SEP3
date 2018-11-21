import controller.repositories.LibraryRepo;
import controller.repositories.LibraryRepository;
import model.Library;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class LibraryRepoTest {

    private LibraryRepo libraryRepo;

    @BeforeEach
    void setup(){
        libraryRepo = LibraryRepository.getInstance();
    }

    @Test
    void getLibraryByIdTest(){
        Library lib  = new Library("ce78ef57-77ec-4bb7-82a2-1a78d3789aef");

        try {
            assertEquals(lib, libraryRepo.get("ce78ef57-77ec-4bb7-82a2-1a78d3789aef"));
        } catch (LibraryRepository.LibraryNotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void getLibraryByIdExceptionTest(){
        assertThrows(LibraryRepository.LibraryNotFoundException.class,() -> libraryRepo.get("doesnotexist"));
    }


}

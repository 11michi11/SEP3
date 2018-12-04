import controller.repositories.LibraryAdminRepo;
import controller.repositories.LibraryAdminRepository;
import model.Library;
import model.LibraryAdmin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LibraryAdministratorRepoTest {


    private LibraryAdminRepo libraryAdminRepo;
    private static final String LIBRARY_ID = "ce78ef57-77ec-4bb7-82a2-1a78d3789aef";

    @BeforeEach
    void setup(){
        libraryAdminRepo = LibraryAdminRepository.getInstance();
        Library lib = new Library("ce78ef57-77ec-4bb7-82a2-1a78d3789aef");
    }


    @Test
    void AddDeleteTest(){
        LibraryAdmin admin;
    }

}

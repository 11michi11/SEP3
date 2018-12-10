import controller.repositories.LibraryAdminRepo;
import controller.repositories.LibraryAdminRepository;
import model.Library;
import model.LibraryAdmin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class LibraryAdministratorRepoTest {


    private LibraryAdminRepo libraryAdminRepo;
    private static final String LIBRARY_ID = "ce78ef57-77ec-4bb7-82a2-1a78d3789aef";

    @BeforeEach
    void setup(){
        libraryAdminRepo = LibraryAdminRepository.getInstance();
    }


    @Test
    void AddDeleteTest(){
        Library lib = new Library("ce78ef57-77ec-4bb7-82a2-1a78d3789aef");
        LibraryAdmin admin = new LibraryAdmin("adminID", lib, "name", "email", "password");

        libraryAdminRepo.add(admin);

        try {
            assertEquals(admin, libraryAdminRepo.get("adminID"));
        } catch (LibraryAdminRepository.LibraryAdminNotFoundException e) {
            fail("Admin can not be added");
        }

        libraryAdminRepo.delete(admin);
    }
}

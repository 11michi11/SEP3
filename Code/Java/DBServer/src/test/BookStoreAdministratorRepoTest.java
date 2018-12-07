import controller.repositories.BookStoreAdminRepo;
import controller.repositories.BookStoreAdminRepository;
import model.BookStore;
import model.BookStoreAdmin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class BookStoreAdministratorRepoTest {

	private BookStoreAdminRepo bookStoreAdminRepo;
	private static final String BOOKSTORE_ID = "eb3777c8-77fe-4acd-962d-6853da2e05e0";

	@BeforeEach
	void setup() {
		bookStoreAdminRepo = BookStoreAdminRepository.getInstance();
	}

	@Test
	void AddDeleteTest() {
		BookStore bookStoreID = new BookStore("eb3777c8-77fe-4acd-962d-6853da2e05e0");
		BookStoreAdmin admin = new BookStoreAdmin("adminID", bookStoreID, "name", "email", "password");

		bookStoreAdminRepo.add(admin);

		try {
			assertEquals(admin, bookStoreAdminRepo.get("adminID"));
		} catch (BookStoreAdminRepository.BookStoreAdminNotFoundException e) {
			fail("Admin can not be added");
		}

		bookStoreAdminRepo.delete(admin);
	}
}

import controller.repositories.CustomerRepo;
import controller.repositories.CustomerRepository;
import model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class CustomerRepoTest {

	private CustomerRepo customerRepo;

	@BeforeEach
	public void setUp() {
		this.customerRepo = new CustomerRepository();
	}

	/** @noinspection SpellCheckingInspection*/
	@Test
	void getCustomerTest() {
		//setup
		Customer customer = new Customer("id", "name", "email", "address", 111, "password");
		try {
			customerRepo.add(customer);
		} catch (CustomerRepository.CustomerEmailException e) {
			fail("Customer can not be added, email already in use");
		}

		//test
		try {
			assertEquals(customer, customerRepo.get("id"));
		} catch (CustomerRepository.CustomerNotFoundException e) {
			fail("No customer");
		}

		//clenup
		customerRepo.delete(customer);
	}
}

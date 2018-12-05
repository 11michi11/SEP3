package controller.repositories;

import model.Customer;

public interface CustomerRepo {

    void add(Customer customer) throws CustomerRepository.CustomerEmailException;

    Customer get(String customerId) throws CustomerRepository.CustomerNotFoundException;

	void delete(Customer customer);

    Customer getByEmail(String email) throws CustomerRepository.CustomerNotFoundException;
}

package controller.repositories;

import model.Customer;

public interface CustomerRepo {

    void add(Customer customer) throws CustomerRepository.CustomerEmailException;
}

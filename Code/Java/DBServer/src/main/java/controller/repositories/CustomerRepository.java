package controller.repositories;

import controller.HibernateAdapter;
import model.Customer;
import org.hibernate.SessionFactory;

import javax.persistence.PersistenceException;

public class CustomerRepository implements CustomerRepo {

    private static CustomerRepository instance;
    private SessionFactory sessionFactory;


    public CustomerRepository() {
        this.sessionFactory = HibernateAdapter.getSessionFactory();
    }

    public static CustomerRepository getInstance() {
        if (instance == null) {
            instance = new CustomerRepository();
        }
        return instance;
    }

    @Override
    public void add(Customer customer) throws CustomerEmailException {
        try {
            HibernateAdapter.addObject(customer);
        } catch (PersistenceException e) {
            throw new CustomerEmailException("Email already in use");
        }
    }

    public static class CustomerEmailException extends Exception {
        public CustomerEmailException(String msg) {
            super(msg);
        }
    }

}

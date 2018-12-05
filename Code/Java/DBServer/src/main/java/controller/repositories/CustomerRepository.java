package controller.repositories;

import controller.HibernateAdapter;
import model.Customer;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.NoResultException;
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

    @Override
    public Customer get(String customerId) throws CustomerNotFoundException {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            Customer customer = (Customer) session.createQuery("FROM Customer where id like :customerId").setParameter("customerId", customerId).getSingleResult();
            tx.commit();
            return customer;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }catch (NoResultException e1){
            throw new CustomerNotFoundException("There is no customer with id:" + customerId);
        }
        throw new CustomerNotFoundException("There is no customer with id:" + customerId);

    }

    @Override
    public void delete(Customer customer) {
        HibernateAdapter.deleteObject(customer);
    }

    @Override
    public Customer getByEmail(String email) throws CustomerNotFoundException {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            Customer customer = (Customer) session.createQuery("FROM Customer where email like :email").setParameter("email", email).getSingleResult();
            tx.commit();
            return customer;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }catch (NoResultException e1){
            throw new CustomerNotFoundException("There is no customer with email:" + email);
        }
        throw new CustomerNotFoundException("There is no customer with email:" + email);


    }

    public static class CustomerEmailException extends Exception {
        public CustomerEmailException(String msg) {
            super(msg);
        }
    }

    public class CustomerNotFoundException extends Exception {
        public CustomerNotFoundException(String msg) {
            super(msg);
        }
    }
}

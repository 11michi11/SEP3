package controller.repositories;

import controller.HibernateAdapter;
import model.*;
import org.hibernate.*;

import java.util.List;
import java.util.UUID;

public class BookStoreOrderRepository implements BookStoreOrderRepo {

    private BookStoreStorageRepo bookStoreStorageRepo;
    private CustomerRepo customerRepo;
    private SessionFactory sessionFactory;
    private static BookStoreOrderRepository instance;

    private BookStoreOrderRepository() {
        bookStoreStorageRepo = BookStoreStorageRepository.getInstance();
        customerRepo = CustomerRepository.getInstance();
        sessionFactory = HibernateAdapter.getSessionFactory();
    }

    public static BookStoreOrderRepository getInstance() {
        if (instance == null) {
            instance = new BookStoreOrderRepository();
        }
        return instance;
    }

    @Override
    public String add(String isbn, String customerId) throws CustomerRepository.CustomerNotFoundException, BookStoreStorageRepository.BookStoreStorageNotFoundException {
        BookStoreOrder bookStoreOrder = createBookStoreOrder(isbn, customerId);

        HibernateAdapter.addObject(bookStoreOrder);
        return bookStoreOrder.getOrderid();
    }

    @Override
    public void delete(String orderId) throws CustomerRepository.CustomerNotFoundException {
        BookStoreOrder bookStoreOrder = get(orderId);

        HibernateAdapter.deleteObject(bookStoreOrder);
    }

    @Override
    public List<BookStoreOrder> get() {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            List<BookStoreOrder> bookStoreOrders = (List<BookStoreOrder>) session.createQuery("FROM BookStoreOrder ").list();
            tx.commit();
            return bookStoreOrders;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public BookStoreOrder get(String orderId) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            BookStoreOrder bookStoreOrder = (BookStoreOrder) session.
                    createQuery("FROM BookStoreOrder where orderid like :orderId ")
                    .setParameter("orderId", orderId).getSingleResult();
            tx.commit();
            return bookStoreOrder;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return null;
    }

    private BookStoreOrder createBookStoreOrder(String isbn, String customerId) throws CustomerRepository.CustomerNotFoundException, BookStoreStorageRepository.BookStoreStorageNotFoundException {
        BookStoreStorage bookStoreStorage = bookStoreStorageRepo.getStorageByBookId(isbn);
        Book book = bookStoreStorage.getBook();
        BookStore bookstore = bookStoreStorage.getBookstore();
        Customer customer = customerRepo.get(customerId);

        String orderId = UUID.randomUUID().toString();
        return new BookStoreOrder(orderId, bookstore, book, customer);
    }
}

package controller.repositories;

import controller.HibernateAdapter;
import model.*;
import org.hibernate.*;
import utils.SendEmail;

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
    public String add(String isbn, String boookstoreId, String customerId) throws CustomerRepository.CustomerNotFoundException, BookStoreStorageRepository.BookStoreStorageNotFoundException {
        BookStoreOrder bookStoreOrder = createBookStoreOrder(isbn, boookstoreId, customerId);

        HibernateAdapter.addObject(bookStoreOrder);
        return bookStoreOrder.getOrderid();
    }

    @Override
    public void delete(String orderId) throws CustomerRepository.CustomerNotFoundException {
        BookStoreOrder bookStoreOrder = get(orderId);

        String title = bookStoreOrder.getBook().getTitle();
        String email = bookStoreOrder.getCustomer().getEmail();

        SendEmail.sendOrderConfirmedEmail(email, title);

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

    @Override
    public List<BookStoreOrderData> getBookStoreOrders(String bookStoreId) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            List<BookStoreOrderData> bookStoreOrders = (List<BookStoreOrderData>) session.createQuery("SELECT " +
                    " new model.BookStoreOrderData(order.orderid, order.book.isbn, order.book.title, order.customer.name, order.customer.email, order.customer.address, order.customer.phoneNum) " +
                    "FROM BookStoreOrder as order WHERE bookStore.bookstoreid like :bookStoreId").setParameter("bookStoreId",bookStoreId).list();
            tx.commit();
            return bookStoreOrders;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return null;     }

    private BookStoreOrder createBookStoreOrder(String isbn, String bookstoreId, String customerId) throws CustomerRepository.CustomerNotFoundException, BookStoreStorageRepository.BookStoreStorageNotFoundException {
       try {
           BookStoreStorage bookStoreStorage = bookStoreStorageRepo.getStoragesByIsbnAndBookstore(isbn, bookstoreId).get(0);
           Book book = bookStoreStorage.getBook();
           BookStore bookstore = bookStoreStorage.getBookstore();
           Customer customer = customerRepo.get(customerId);

           String orderId = UUID.randomUUID().toString();
           return new BookStoreOrder(orderId, bookstore, book, customer);
       }catch (IndexOutOfBoundsException e) {
           throw new BookStoreStorageRepository.BookStoreStorageNotFoundException("There is no such book in that bookstore");
       }
    }
}

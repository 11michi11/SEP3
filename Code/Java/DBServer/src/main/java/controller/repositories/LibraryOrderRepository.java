package controller.repositories;

import controller.HibernateAdapter;
import model.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import utils.SendEmail;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class LibraryOrderRepository implements LibraryOrderRepo {

    private SessionFactory sessionFactory;
    private LibraryStorageRepo libraryStorageRepo;
    private CustomerRepo customerRepo;
    private static LibraryOrderRepository instance;

    private LibraryOrderRepository() {
        this.sessionFactory = HibernateAdapter.getSessionFactory();
        this.libraryStorageRepo = LibraryStorageRepository.getInstance();
        this.customerRepo = CustomerRepository.getInstance();
    }

    public static LibraryOrderRepo getInstance() {
        if (instance == null) {
            instance = new LibraryOrderRepository();
        }
        return instance;
    }


    @Override
    public String add(String bookId, String customerId) throws LibraryStorageRepository.LibraryStorageNotFoundException, CustomerRepository.CustomerNotFoundException {
        LibraryOrder order = createLibraryOrder(bookId, customerId);

        HibernateAdapter.addObject(order);

        //marking the book as unavailable
        LibraryStorage libraryStorage = order.getLibraryStorage();
        libraryStorage.setAvailable(false);
        HibernateAdapter.updateObject(libraryStorage);

        return order.getOrderid();
    }

    @Override
    public void delete(String orderId) {
        LibraryOrder order = get(orderId);

        String title = order.getBook().getTitle();
        String email = order.getCustomer().getEmail();

        SendEmail.sendBookReturnEmail(email, title);

        HibernateAdapter.deleteObject(order);

        //marking the book available
        LibraryStorage libraryStorage = order.getLibraryStorage();
        libraryStorage.setAvailable(true);
        HibernateAdapter.updateObject(libraryStorage);
    }

    private LibraryOrder createLibraryOrder(String bookId, String customerId) throws LibraryStorageRepository.LibraryStorageNotFoundException, CustomerRepository.CustomerNotFoundException {
        LibraryStorage libraryStorage = libraryStorageRepo.getStorageByBookId(bookId);
        Book book = libraryStorage.getBook();
        Library library = libraryStorage.getLibrary();
        Customer customer = customerRepo.get(customerId);
        Calendar dateOfOrder = Calendar.getInstance();
        Calendar returnDate = Calendar.getInstance();
        returnDate.add(Calendar.DAY_OF_MONTH, 30);

        String orderId = UUID.randomUUID().toString();
        return new LibraryOrder(orderId, libraryStorage, library, book, customer, dateOfOrder, returnDate);
    }

    @Override
    public List<LibraryOrder> get() {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            List<LibraryOrder> libraryOrders = (List<LibraryOrder>) session.createQuery("FROM LibraryOrder ").list();
            tx.commit();
            return libraryOrders;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public LibraryOrder get(String orderId) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            LibraryOrder libraryOrder =
                    (LibraryOrder) session.createQuery("FROM LibraryOrder where orderid like :orderId")
                            .setParameter("orderId", orderId).getSingleResult();
            tx.commit();
            return libraryOrder;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<LibraryOrderData> getLibraryOrders(String libraryId) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            List<LibraryOrderData> libraryOrders = (List<LibraryOrderData>) session.createQuery("SELECT " +
                    " new model.LibraryOrderData(order.orderid, order.book.isbn, order.book.title, order.dateOfOrder, order.returnDate, order.customer.name, order.customer.email, order.customer.address, order.customer.phoneNum) "
                    +"FROM LibraryOrder as order WHERE library.libraryID like :libraryId").setParameter("libraryId",libraryId).list();
            tx.commit();
            return libraryOrders;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return null;
    }
}

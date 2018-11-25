package controller.repositories;

import controller.HibernateAdapter;
import model.Book;
import model.LibraryOrder;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class LibraryOrderRepository implements LibraryOrderRepo{

    private SessionFactory sessionFactory;

    public LibraryOrderRepository() {
        this.sessionFactory = HibernateAdapter.getSessionFactory();
    }

    @Override
    public void add(String bookId, String libraryId, String customerId) {

    }

    @Override
    public List<LibraryOrder> get() {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            List<LibraryOrder> libraryOrders= (List<LibraryOrder>) session.createQuery("FROM LibraryOrder ").list();
            tx.commit();
            return libraryOrders;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        LibraryOrderRepository repository = new LibraryOrderRepository();

        repository.get().forEach(System.out::println);


    }

}

package controller.repositories;

import controller.HibernateAdapter;
import model.BookStore;
import model.Library;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class BookStoreRepository implements BookStoreRepo {
    private SessionFactory sessionFactory;

    public BookStoreRepository(SessionFactory sessionFactory) {
        this.sessionFactory = HibernateAdapter.getSessionFactory();
    }

    @Override
    public BookStore get(String bookStoreId) throws BookStoreNotFoundException {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            BookStore bookStore = (BookStore) session.createQuery("FROM BookStore where bookstoreid like :bookstoreid").setParameter("bookstoreid", bookStoreId).getSingleResult();
            tx.commit();
            return bookStore;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        throw new BookStoreNotFoundException("There is no book store with id: " + bookStoreId);
    }


    public class BookStoreNotFoundException extends Exception {

        public BookStoreNotFoundException(String msg) {
            super(msg);
        }
    }
}

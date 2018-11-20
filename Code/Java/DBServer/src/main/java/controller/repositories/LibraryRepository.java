package controller.repositories;

import controller.HibernateAdapter;
import model.Book;
import model.Library;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class LibraryRepository implements LibraryRepo {

    private SessionFactory sessionFactory;

    public LibraryRepository(SessionFactory sessionFactory) {
        this.sessionFactory = HibernateAdapter.getSessionFactory();
    }

    @Override
    public Library get(String libraryId) throws LibraryNotFoundException {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            Library library = (Library) session.createQuery("FROM Library where libraryID like :libraryid").setParameter("libraryid", libraryId).getSingleResult();
            tx.commit();
            return library;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        throw new LibraryNotFoundException("There is no library with id: " + libraryId);
    }

    public class LibraryNotFoundException extends Exception{
        public LibraryNotFoundException(String msg) {
            super(msg);
        }
    }
}

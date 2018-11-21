package controller.repositories;

import controller.HibernateAdapter;
import model.Book;
import model.Library;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class LibraryRepository implements LibraryRepo {

    private static LibraryRepository instance;
    private SessionFactory sessionFactory;

    public LibraryRepository() {
        this.sessionFactory = HibernateAdapter.getSessionFactory();
    }

    public static LibraryRepository getInstance() {
        if (instance == null) {
            instance = new LibraryRepository();
        }
        return instance;
    }

    @Override
    public Library get(String libraryId) throws LibraryNotFoundException {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            Library library = (Library) session.createQuery("FROM Library where libraryID like :libraryid").setParameter("libraryid", libraryId).getSingleResult();
            tx.commit();
            if (library == null)
                throw new LibraryNotFoundException("There is no library with id: " + libraryId);
            return library;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }catch(javax.persistence.NoResultException e){
            throw new LibraryNotFoundException("There is no library with id: " + libraryId);
        }
        throw new LibraryNotFoundException("There is no library with id: " + libraryId);
    }

    public class LibraryNotFoundException extends Exception{
        public LibraryNotFoundException(String msg) {
            super(msg);
        }
    }
}

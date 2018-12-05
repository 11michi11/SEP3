package controller.repositories;

import controller.HibernateAdapter;
import model.LibraryAdmin;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class LibraryAdminRepository implements LibraryAdminRepo {
    private SessionFactory sessionFactory;
    private static LibraryAdminRepo instance;

    private LibraryAdminRepository() {
        sessionFactory = HibernateAdapter.getSessionFactory();
    }

    public static LibraryAdminRepo getInstance() {
        if (instance == null)
            instance = new LibraryAdminRepository();
        return instance;
    }

    @Override
    public LibraryAdmin get(String id) throws LibraryAdminNotFoundException {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            LibraryAdmin admin = (LibraryAdmin) session.createQuery("FROM LibraryAdmin where adminId like :id").setParameter("id", id).getSingleResult();
            tx.commit();
            if (admin == null)
                throw new LibraryAdminNotFoundException("There is no library admin with id: " + id);
            return admin;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } catch (javax.persistence.NoResultException e) {
            throw new LibraryAdminNotFoundException("There is no library admin with id: " + id);
        }
        throw new LibraryAdminNotFoundException("There is no library admin with id: " + id);
    }

    @Override
    public void add(LibraryAdmin admin) {
        HibernateAdapter.addObject(admin);
    }

    @Override
    public void delete(LibraryAdmin admin) {
        HibernateAdapter.deleteObject(admin);
    }

    public class LibraryAdminNotFoundException extends Throwable {
        public LibraryAdminNotFoundException(String msg) {
            super(msg);
        }
    }
}

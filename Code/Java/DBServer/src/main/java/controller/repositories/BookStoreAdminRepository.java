package controller.repositories;

import controller.HibernateAdapter;
import model.BookStoreAdmin;
import org.hibernate.*;

public class BookStoreAdminRepository implements BookStoreAdminRepo{


	private SessionFactory sessionFactory;
	private static BookStoreAdminRepo instance;

	public BookStoreAdminRepository() {
		sessionFactory = HibernateAdapter.getSessionFactory();
	}

	public static BookStoreAdminRepo getInstance() {
		if (instance == null)
			instance = new BookStoreAdminRepository();
		return instance;
	}

	@Override
	public void add(BookStoreAdmin admin) {
		HibernateAdapter.addObject(admin);
	}

	@Override
	public void delete(BookStoreAdmin admin) {
		HibernateAdapter.deleteObject(admin);
	}

	@Override
	public BookStoreAdmin get(String id) throws BookStoreAdminNotFoundException {
		Transaction tx = null;
		try (Session session = sessionFactory.openSession()) {
			tx = session.beginTransaction();
			BookStoreAdmin admin = (BookStoreAdmin) session.createQuery("FROM BookStoreAdmin where adminId like :id").setParameter("id", id).getSingleResult();
			tx.commit();
			if (admin == null)
				throw new BookStoreAdminNotFoundException("There is no bookstore admin with id: "  + id);
			return admin;
		} catch (HibernateException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		} catch (javax.persistence.NoResultException e) {
			throw new BookStoreAdminNotFoundException("There is no bookstore admin with id: " + id);
		}
		throw new BookStoreAdminNotFoundException("There is no bookstore admin with id: " + id);
	}

    @Override
    public BookStoreAdmin getByEmail(String email) throws BookStoreAdminNotFoundException {
		Transaction tx = null;
		try (Session session = sessionFactory.openSession()) {
			tx = session.beginTransaction();
			BookStoreAdmin admin = (BookStoreAdmin) session.createQuery("FROM BookStoreAdmin where email like :email").setParameter("email", email).getSingleResult();
			tx.commit();
			if (admin == null)
				throw new BookStoreAdminNotFoundException("There is no bookstore admin with email: "  + email);
			return admin;
		} catch (HibernateException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		} catch (javax.persistence.NoResultException e) {
			throw new BookStoreAdminNotFoundException("There is no bookstore admin with email: " + email);
		}
		throw new BookStoreAdminNotFoundException("There is no bookstore admin with email: " + email);

    }

    public class BookStoreAdminNotFoundException extends Throwable {
		public BookStoreAdminNotFoundException(String message) {
			super(message);
		}
	}
}

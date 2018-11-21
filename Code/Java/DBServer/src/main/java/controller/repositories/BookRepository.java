package controller.repositories;

import controller.HibernateAdapter;
import model.Book;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.LinkedList;
import java.util.List;

public class BookRepository implements BookRepo{

    private SessionFactory sessionFactory;

    private static BookRepository instance;

    private BookRepository() {
        this.sessionFactory = HibernateAdapter.getSessionFactory();
    }

    public static BookRepository getInstance() {
        if (instance == null) {
            instance = new BookRepository();
        }
        return instance;
    }

    @Override
    public void add(Book book) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.save(book);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String isbn) throws BookNotFoundException {
        Book book = get(isbn);

        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.delete(book);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void saveOrUpdate(Book book){
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.saveOrUpdate(book);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public Book get(String isbn) throws BookNotFoundException {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            Book book = (Book) session.createQuery("FROM Book where isbn like :isbn").setParameter("isbn", isbn).getSingleResult();
            tx.commit();
            return book;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        throw new BookNotFoundException("There is no book with isbn: " + isbn);
    }

    @Override
    public List<Book> search(String searchTerm) {
        throw new NotImplementedException();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Book> advancedSearch(String isbn, String title, String author, int year, Book.Category category) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            List<Book> searchedBooks = session.createQuery("select new model.Book(isbn, title, author, year, category) from Book where " +
                    "isbn like :isbn or " +
                    "lower(title) like :title or " +
                    "lower(author) like :author or " +
                    "year = :year or " +
                    "category like :category")
                    .setParameter("isbn", "%" + isbn + "%")
                    .setParameter("title", "%" + title.toLowerCase() + "%")
                    .setParameter("author", "%" + author.toLowerCase() + "%")
                    .setParameter("year", year)
                    .setParameter("category", category)
                    .list();
            tx.commit();
            return searchedBooks;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return new LinkedList<>();
    }


    public static class BookNotFoundException extends Exception {
        public BookNotFoundException(String s) {
            super(s);
        }
    }

}

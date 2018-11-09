package controller;

import model.Book;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import java.util.LinkedList;
import java.util.List;

public class HibernateAdapter implements DBProxy {
    private final SessionFactory ourSessionFactory;

    public HibernateAdapter() {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();
            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }

    public List<Book> getAllBooks() {
        Transaction tx = null;
        try (Session session = ourSessionFactory.openSession()) {
            tx = session.beginTransaction();
            List<Book> book = session.createQuery("FROM Book ").list();
            tx.commit();
            return book;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return new LinkedList<>();
    }

    @SuppressWarnings("unchecked")
    public List<Book> advancedSearch(String isbn, String title, String author, int year, Book.Category category) {
        Transaction tx = null;
        try (Session session = ourSessionFactory.openSession()) {
            tx = session.beginTransaction();
            List<Book> searchedBooks = session.createQuery("select new model.Book(isbn, title, author, year, category) from Book where " +
                    "isbn like :isbn or " +
                    "title like :title or " +
                    "author like :author or " +
                    "year = :year or " +
                    "category like :category")
                    .setParameter("isbn", "%" + isbn + "%")
                    .setParameter("title", "%" + title + "%")
                    .setParameter("author", "%" + author + "%")
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



}
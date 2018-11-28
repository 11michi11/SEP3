package controller.repositories;

import controller.HibernateAdapter;
import model.Book;
import org.apache.lucene.search.Query;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;

import javax.persistence.NoResultException;
import java.util.*;

public class BookRepository implements BookRepo {

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
    public void saveOrUpdate(Book book) {
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
        } catch (NoResultException e1) {
            throw new BookNotFoundException("There is no book with isbn: " + isbn);
        }
        throw new BookNotFoundException("There is no book with isbn: " + isbn);
    }

    @Override
    public List<Book> search(String searchTerm) {
        if(searchTerm.equals(""))
            return Collections.emptyList();

        String[] fields = {"bookIsbn", "title", "author", "year", "category"};

        return (List<Book>) HibernateAdapter.executeQuery(searchTerm, Book.class, fields);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Book> advancedSearch(String isbn, String title, String author, int year, Book.Category category) {
        if(isbn.equals(""))
            isbn = "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz";
        if(title.equals(""))
            title = "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz";
        if(author.equals(""))
            author = "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz";


        List<Book> isbnBooks = HibernateAdapter.executeQuery(isbn, Book.class, "isbn");
        List<Book> titleBooks = HibernateAdapter.executeQuery(title, Book.class, "title");
        List<Book> authorBooks = HibernateAdapter.executeQuery(author, Book.class, "author");
        List<Book> yearBooks = HibernateAdapter.executeQuery(Integer.toString(year), Book.class, "year");
        List<Book> categoryBooks = HibernateAdapter.executeQuery(category.toString(), Book.class, "category");


        Set<Book> books = new HashSet<>(isbnBooks);
        books.addAll(titleBooks);
        books.addAll(authorBooks);
        books.addAll(yearBooks);
        books.addAll(categoryBooks);


        return new LinkedList<>(books);
    }


    public static class BookNotFoundException extends Exception {
        public BookNotFoundException(String s) {
            super(s);
        }
    }

}

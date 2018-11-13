package controller;

import model.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public DetailedBook getBookDetails(String isbn) {

        List<LibraryStorage> libraryStorages = getLibrariesStorageByIsbn(isbn);
        List<BookStoreStorage> bookStoreStorages = getBookStoresStorageByIsbn(isbn);

        libraryStorages.forEach(System.out::println);

        //There is only one book
        Book book = libraryStorages.get(0).getId().getBook();

        List<BookStore> bookStores = bookStoreStorages.stream()
                .filter(libraryStorage -> libraryStorage.getId().getBook().getIsbn().equals(book.getIsbn()))
                .map(libraryStorage -> libraryStorage.getId().getBookstore()).collect(Collectors.toList());

        return new DetailedBook(book, libraryStorages, bookStores);
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

    @Override
    public Book getBookByIsbn(String isbn) throws BookNotFoundException {
        Transaction tx = null;
        try (Session session = ourSessionFactory.openSession()) {
            tx = session.beginTransaction();
            Book book = (Book) session.createQuery("FROM Book where isbn like :isbn").setParameter("isbn" , isbn).getSingleResult();
            tx.commit();
            return book;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        throw new BookNotFoundException("There is no book with isbn: " + isbn);
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


    public List<LibraryStorage> getLibrariesStorage() {
        Transaction tx = null;
        try (Session session = ourSessionFactory.openSession()) {
            tx = session.beginTransaction();
            List<LibraryStorage> storages = session.createQuery("FROM LibraryStorage ").list();
            tx.commit();
            return storages;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return new LinkedList<>();
    }

    @SuppressWarnings("unchecked")
    public List<LibraryStorage> getLibrariesStorageByIsbn(String isbn) {
        Transaction tx = null;
        try (Session session = ourSessionFactory.openSession()) {
            tx = session.beginTransaction();
            List<LibraryStorage> storages = session.createQuery("FROM LibraryStorage where isbn like :isbn")
                    .setParameter("isbn", isbn)
                    .list();
            tx.commit();
            return storages;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return new LinkedList<>();
    }

    public List<BookStoreStorage> getBookStoresStorage() {
        Transaction tx = null;
        try (Session session = ourSessionFactory.openSession()) {
            tx = session.beginTransaction();
            List<BookStoreStorage> storages = session.createQuery("FROM BookStoreStorage ").list();
            tx.commit();
            return storages;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return new LinkedList<>();
    }

    @SuppressWarnings("unchecked")
    public List<BookStoreStorage> getBookStoresStorageByIsbn(String isbn) {
        Transaction tx = null;
        try (Session session = ourSessionFactory.openSession()) {
            tx = session.beginTransaction();
            List<BookStoreStorage> storages = session.createQuery("FROM BookStoreStorage where isbn like :isbn")
                    .setParameter("isbn", isbn)
                    .list();
            tx.commit();
            return storages;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return new LinkedList<>();
    }

    @Override
    public void addBookToLibrary(LibraryStorage libraryBook){
        updateObject(libraryBook.getId().getBook());
        addObject(libraryBook);
    }

    @Override
    public void addBookToBookStore(BookStoreStorage bookStoreBook){
        updateObject(bookStoreBook.getId().getBook());
        addObject(bookStoreBook);
    }


    private void addObject(Object obj){
        Transaction tx = null;
        try (Session session = ourSessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.save(obj);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void deleteBookFromLibrary(LibraryStorage libraryBook){
        deleteObject(libraryBook);
    }

    @Override
    public void deleteBookFromBookStore(BookStoreStorage bookStoreBook){
        deleteObject(bookStoreBook);
    }

    private void deleteObject(Object obj){
        Transaction tx = null;
        try (Session session = ourSessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.delete(obj);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    private void updateObject(Object obj){
        Transaction tx = null;
        try (Session session = ourSessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.update(obj);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        HibernateAdapter db = new HibernateAdapter();
        try {
            System.out.println(db.getBookByIsbn("978-83-8116-1"));
        } catch (BookNotFoundException e) {
            e.printStackTrace();
        }


    }


    class BookNotFoundException extends Exception {
        public BookNotFoundException(String s) {
            super(s);
        }
    }
}
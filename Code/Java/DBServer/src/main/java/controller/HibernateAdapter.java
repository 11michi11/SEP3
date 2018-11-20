package controller;

import model.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.PersistenceException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class HibernateAdapter implements DBProxy {
    private static final SessionFactory ourSessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();
            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return ourSessionFactory;
    }

    @SuppressWarnings("unchecked")
    public List<Book> advancedSearch(String isbn, String title, String author, int year, Book.Category category) {
        Transaction tx = null;
        try (Session session = ourSessionFactory.openSession()) {
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

    @SuppressWarnings("unchecked")
    @Override
    public List<Book> advancedSearchInLibrary(String libraryId, String isbn, String title, String author, int year, Book.Category category) {
        Transaction tx = null;
        try (Session session = ourSessionFactory.openSession()) {
            tx = session.beginTransaction();
            List<Book> searchedBooks = session.createQuery("select new model.Book(s.id.book.isbn, s.id.book.title, s.id.book.author, s.id.book.year, s.id.book.category) from LibraryStorage as s where " +
                    "s.id.book.isbn like :isbn or " +
                    "lower(s.id.book.title) like :title or " +
                    "lower(s.id.book.author) like :author or " +
                    "s.id.book.year = :year or " +
                    "s.id.book.category like :category and " +
                    "s.id.library.libraryID like :libraryid")
                    .setParameter("isbn", "%" + isbn + "%")
                    .setParameter("title", "%" + title.toLowerCase() + "%")
                    .setParameter("author", "%" + author.toLowerCase() + "%")
                    .setParameter("year", year)
                    .setParameter("category", category)
                    .setParameter("libraryid", libraryId)
                    .list();
            tx.commit();
            return searchedBooks;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return new LinkedList<>();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Book> advancedSearchInBookStore(String bookStoreId, String isbn, String title, String author, int year, Book.Category category) {
        Transaction tx = null;
        try (Session session = ourSessionFactory.openSession()) {
            tx = session.beginTransaction();
            List<Book> searchedBooks = session.createQuery("select new model.Book(s.id.book.isbn, s.id.book.title, s.id.book.author, s.id.book.year, s.id.book.category) from BookStoreStorage as s where " +
                    "s.id.book.isbn like :isbn or " +
                    "lower(s.id.book.title) like :title or " +
                    "lower(s.id.book.author) like :author or " +
                    "s.id.book.year = :year or " +
                    "s.id.book.category like :category or " +
                    "s.id.bookstore.bookstoreid like :bookStoreId")
                    .setParameter("isbn", "%" + isbn + "%")
                    .setParameter("title", "%" + title.toLowerCase() + "%")
                    .setParameter("author", "%" + author.toLowerCase() + "%")
                    .setParameter("year", year)
                    .setParameter("category", category)
                    .setParameter("bookStoreId", bookStoreId)
                    .list();
            tx.commit();
            return searchedBooks;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return new LinkedList<>();
    }

    @Override
    public DetailedBook getBookDetails(String isbn) throws BookNotFoundException {

        List<LibraryStorage> libraryStorages = getLibrariesStorageByIsbn(isbn);
        List<BookStoreStorage> bookStoreStorages = getBookStoresStorageByIsbn(isbn);

        libraryStorages.forEach(System.out::println);

        //There is only one book
        try {
            Book book = libraryStorages.get(0).getId().getBook();

            List<BookStore> bookStores = bookStoreStorages.stream()
                    .filter(libraryStorage -> libraryStorage.getId().getBook().getIsbn().equals(book.getIsbn()))
                    .map(libraryStorage -> libraryStorage.getId().getBookstore()).collect(Collectors.toList());

            return new DetailedBook(book, libraryStorages, bookStores);
        } catch (IndexOutOfBoundsException e) {
            throw new BookNotFoundException("There is no book with isbn:" + isbn);
        }
    }

    @Override
    public Book getBookByIsbn(String isbn) throws BookNotFoundException {
        Transaction tx = null;
        try (Session session = ourSessionFactory.openSession()) {
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
    public Book getBookByLibraryBookId(String bookid) throws BookNotFoundException {
        Transaction tx = null;
        try (Session session = ourSessionFactory.openSession()) {
            tx = session.beginTransaction();
            LibraryStorage libraryStorage = (LibraryStorage) session.createQuery("FROM LibraryStorage where bookid like :bookid").setParameter("bookid", bookid).getSingleResult();
            tx.commit();
            return libraryStorage.getId().getBook();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        throw new BookNotFoundException("There is no book with id: " + bookid);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<LibraryStorage> getLibrariesStorageByIsbnAndLibrary(String isbn, String libraryid) {
        Transaction tx = null;
        try (Session session = ourSessionFactory.openSession()) {
            tx = session.beginTransaction();
            List<LibraryStorage> storages = session.createQuery("FROM LibraryStorage as s where " +
                    "s.id.book.isbn like :isbn and " +
                    "s.id.library.libraryID like :libraryid")
                    .setParameter("isbn", isbn)
                    .setParameter("libraryid", libraryid)
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
    public void addBookToLibrary(LibraryStorage libraryBook) {
        updateObject(libraryBook.getId().getBook());
        addObject(libraryBook);
    }

    @Override
    public void addBookToBookStore(BookStoreStorage bookStoreBook) {
        updateObject(bookStoreBook.getId().getBook());
        addObject(bookStoreBook);
    }

    @Override
    public void deleteBookFromLibrary(LibraryStorage libraryBook) {
        deleteObject(libraryBook);
    }

    @Override
    public void deleteBookFromBookStore(BookStoreStorage bookStoreBook) {
        deleteObject(bookStoreBook);
    }

    @Override
    public void addCustomer(Customer customer) throws CustomerEmailException {
        try {
            addObject(customer);
        } catch (PersistenceException e) {
            throw new CustomerEmailException("Email already in use");
        }
    }

    public static void addObject(Object obj) {
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

    public static void updateObject(Object obj) {
        Transaction tx = null;
        try (Session session = ourSessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.saveOrUpdate(obj);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public static void deleteObject(Object obj) {
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

    public static void main(String[] args) {
        HibernateAdapter db = new HibernateAdapter();

        Book book = new Book("642554546", "title", "author", 234, Book.Category.Fantasy);
        BookStore bs = new BookStore("eb3777c8-77fe-4acd-962d-6853da2e05e0");

        BookStoreStorageID id = new BookStoreStorageID(book, bs);

        BookStoreStorage storage = new BookStoreStorage(id);

        try {
            db.getBookDetails("doesnotexist");
        } catch (BookNotFoundException e) {
            e.printStackTrace();
        }
//        db.deleteBookFromBookStore(storage);
    }

    static class BookNotFoundException extends Exception {
        public BookNotFoundException(String s) {
            super(s);
        }
    }

    class CustomerEmailException extends Exception {
        public CustomerEmailException(String msg) {
            super(msg);
        }
    }
}
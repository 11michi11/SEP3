package controller;

import controller.repositories.BookRepository;
import controller.repositories.BookRepository.BookNotFoundException;
import controller.repositories.CustomerRepository;
import controller.repositories.CustomerRepository.CustomerEmailException;
import model.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
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

    @Override
    public List<Book> search(String searchTerm) {
        return null;
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
    public void addBookToLibrary(Book book, String libraryid) {
        Library lib = new Library(libraryid);
        String bookid = UUID.randomUUID().toString();
        LibraryStorageID libId = new LibraryStorageID(book, lib, bookid);
        LibraryStorage libraryStorage = new LibraryStorage(libId, true);

        updateObject(book);
        addObject(libraryStorage);
    }

    @Override
    public void addBookToBookStore(Book book, String institutionId) {
        BookStore bookStore = new BookStore(institutionId);
        BookStoreStorageID bookStoreId = new BookStoreStorageID(book, bookStore);
        BookStoreStorage bookStoreStorage = new BookStoreStorage(bookStoreId);

        updateObject(book);
        addObject(bookStoreStorage);
    }

    @Override
    public void deleteBookFromLibrary(String bookId, String libraryId) throws BookNotFoundException {
        Book book = getBookByLibraryBookId("bookid");
        Library lib = new Library(libraryId);
        LibraryStorageID libId = new LibraryStorageID(book, lib, bookId);
        LibraryStorage libraryStorage = new LibraryStorage(libId, true);

        deleteObject(libraryStorage);
    }

    @Override
    public void deleteBookFromBookStore(String isbn, String bookStoreId) throws BookNotFoundException {
        Book book = getBookByIsbn(isbn);
        BookStore bookStore = new BookStore(bookStoreId);
        BookStoreStorageID id = new BookStoreStorageID(book, bookStore);
        BookStoreStorage bookStoreStorage = new BookStoreStorage(id);

        deleteObject(bookStoreStorage);
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

    private static void rebuildLuceneIndex() throws InterruptedException {
        HibernateAdapter db = new HibernateAdapter();
        SessionFactory sessionFactory = HibernateAdapter.getSessionFactory();
        FullTextSession fullTextSession = Search.getFullTextSession(sessionFactory.openSession());
        fullTextSession.createIndexer().startAndWait();
    }

    public static void main(String[] args) throws InterruptedException {
        SessionFactory sessionFactory = HibernateAdapter.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        EntityManager em = session.getEntityManagerFactory().createEntityManager();
        FullTextEntityManager fullTextEntityManager =
                org.hibernate.search.jpa.Search.getFullTextEntityManager(em);
        em.getTransaction().begin();

// create native Lucene query unsing the query DSL
// alternatively you can write the Lucene query using the Lucene query parser
// or the Lucene programmatic API. The Hibernate Search DSL is recommended though
        QueryBuilder qb = fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder().forEntity(Book.class).get();
        org.apache.lucene.search.Query luceneQuery = qb.keyword()
                .onFields("title", "bookIsbn", "author", "year")
                .ignoreFieldBridge()
                .matching("java fantasy")
                .createQuery();

// wrap Lucene query in a javax.persistence.Query
        javax.persistence.Query jpaQuery =
                fullTextEntityManager.createFullTextQuery(luceneQuery, Book.class);

// execute search
        List result = jpaQuery.getResultList();

        em.getTransaction().commit();
        em.close();

        System.out.println(result);
//      HibernateAdapter.rebuildLuceneIndex();
    }

}
package controller.repositories;

import controller.HibernateAdapter;
import model.Book;
import model.BookStore;
import model.BookStoreStorage;
import model.BookStoreStorageID;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.LinkedList;
import java.util.List;

public class BookStoreStorageRepository implements BookStoreStorageRepo {


    private static BookStoreStorageRepository instance;
    private SessionFactory sessionFactory;
    private BookStoreRepo bookStoreRepo;
    private BookRepo bookRepo;


    public BookStoreStorageRepository() {
        this.sessionFactory = HibernateAdapter.getSessionFactory();
        this.bookRepo = BookRepository.getInstance();
        this.bookStoreRepo = BookStoreRepository.getInstance();
    }

    public static BookStoreStorageRepository getInstance() {
        if (instance == null) {
            instance = new BookStoreStorageRepository();
        }
        return instance;
    }

    @Override
    public void addBookToBookStore(Book book, String bookStoreId) throws BookStoreRepository.BookStoreNotFoundException {
        //Save book to Book table
        bookRepo.saveOrUpdate(book);

        BookStore bookStore = bookStoreRepo.get(bookStoreId);
        BookStoreStorageID id = new BookStoreStorageID(book, bookStore);

        BookStoreStorage bookStoreStorage = new BookStoreStorage(id);
        HibernateAdapter.addObject(bookStoreStorage);
    }

    @Override
    public void deleteBookFromBookStore(String isbn, String bookStoreId) throws BookRepository.BookNotFoundException, BookStoreRepository.BookStoreNotFoundException {
        Book book = bookRepo.get(isbn);

        BookStore bookStore = bookStoreRepo.get(bookStoreId);
        BookStoreStorageID id = new BookStoreStorageID(book, bookStore);

        BookStoreStorage bookStoreStorage = new BookStoreStorage(id);
        HibernateAdapter.deleteObject(bookStoreStorage);
    }

    @Override
    public List<Book> search(String searchTerm) {
        throw new NotImplementedException();
    }

    @Override
    public List<Book> advancedSearch(String bookStoreId, String isbn, String title, String author, int year, Book.Category category) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
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
    public List<BookStoreStorage> getBookStoresStorageByIsbn(String isbn) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
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
}

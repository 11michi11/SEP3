package controller.repositories;

import controller.HibernateAdapter;
import model.Book;
import model.BookStore;
import model.BookStoreStorage;
import model.LibraryStorage;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    public String addBookToBookStore(Book book, String bookStoreId) throws BookStoreRepository.BookStoreNotFoundException, BookAlreadyInBookStoreException {
        //Save book to Book table
        bookRepo.saveOrUpdate(book);

        BookStore bookStore = bookStoreRepo.get(bookStoreId);

        List<BookStoreStorage> storagesByIsbnAndBookstore = getStoragesByIsbnAndBookstore(book.getIsbn(), bookStoreId);
        if (storagesByIsbnAndBookstore.size() > 0)
            throw new BookAlreadyInBookStoreException("Book is already in that bookstore");

        String id = UUID.randomUUID().toString();

        BookStoreStorage bookStoreStorage = new BookStoreStorage(id, bookStore, book);
        try {
            HibernateAdapter.addObject(bookStoreStorage);
        } catch (javax.persistence.PersistenceException e) {
            throw new BookAlreadyInBookStoreException("Book is already in that bookstore");
        }
        return id;
    }

    @Override
    public void deleteBookFromBookStore(String bookid) throws BookRepository.BookNotFoundException, BookStoreRepository.BookStoreNotFoundException, BookStoreStorageNotFoundException {
        BookStoreStorage storage = getStorageByBookId(bookid);
        Book book = storage.getBook();

        BookStore bookStore = storage.getBookstore();
        BookStoreStorage bookStoreStorage = new BookStoreStorage(bookid, bookStore, book);
        HibernateAdapter.deleteObject(bookStoreStorage);
    }

    @Override
    public List<BookStoreStorage> getStoragesByIsbnAndBookstore(String isbn, String bookstoreId) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            List<BookStoreStorage> storages = session.createQuery("FROM BookStoreStorage as s where " +
                    "s.book.isbn like :isbn and " +
                    "s.bookstore.bookstoreid like :bookstoreid")
                    .setParameter("isbn", isbn)
                    .setParameter("bookstoreid", bookstoreId)
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
    public List<Book> search(String searchTerm, String  bookstoreId) {
        if(searchTerm.equals(""))
            return Collections.emptyList();

        List<BookStoreStorage> list = HibernateAdapter.searchInBookStore(searchTerm, bookstoreId);
        return list.stream().map(BookStoreStorage::getBook).collect(Collectors.toList());
    }

    @Override
    public List<Book> advancedSearch(String bookStoreId, String isbn, String title, String author, int year, Book.Category category) {
        String noMatchString = "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz";
        if (isbn.equals(""))
            isbn = noMatchString;
        if (title.equals(""))
            title = noMatchString;
        if (author.equals(""))
            author = noMatchString;

        List<BookStoreStorage> list = HibernateAdapter.advancedSearchInBookStore(bookStoreId, isbn, title, author, year,category);
        return list.stream().map(BookStoreStorage::getBook).collect(Collectors.toList());
    }

    @Override
    public List<BookStoreStorage> getBookStoresStorageByIsbn(String isbn) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            List<BookStoreStorage> storages = session.createQuery("FROM BookStoreStorage as s where s.book.isbn like :isbn")
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
    public BookStoreStorage getStorageByBookId(String bookid) throws BookStoreStorageNotFoundException {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            BookStoreStorage ids = (BookStoreStorage) session.createQuery("FROM BookStoreStorage as s where " +
                    "s.bookid like :bookid")
                    .setParameter("bookid", bookid)
                    .getSingleResult();
            tx.commit();
            return ids;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }catch(javax.persistence.NoResultException e){
            throw new BookStoreStorageNotFoundException("There is no such book in that bookstore/ isbn: " + bookid);
        }

        throw new BookStoreStorageNotFoundException("There is no bookstore storage with isbn: " + bookid);

    }

    public class BookAlreadyInBookStoreException extends Exception {
        public BookAlreadyInBookStoreException(String msg) {
            super(msg);
        }
    }

    public static class BookStoreStorageNotFoundException extends Exception {
        public BookStoreStorageNotFoundException(String msg) {
            super(msg);
        }
    }
}

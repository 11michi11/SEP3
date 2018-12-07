package controller.repositories;

import controller.HibernateAdapter;
import model.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LibraryStorageRepository implements LibraryStorageRepo {

    private static LibraryStorageRepository instance;
    private SessionFactory sessionFactory;
    private LibraryRepo libraryRepo;
    private BookRepo bookRepo;

    private LibraryStorageRepository() {
        this.sessionFactory = HibernateAdapter.getSessionFactory();
        this.bookRepo = BookRepository.getInstance();
        this.libraryRepo = LibraryRepository.getInstance();
    }

    public static LibraryStorageRepository getInstance() {
        if (instance == null) {
            instance = new LibraryStorageRepository();
        }
        return instance;
    }

    @Override
    public String addBookToLibrary(Book book, String libraryId) throws LibraryRepository.LibraryNotFoundException {
        //Save book to Book table
        bookRepo.saveOrUpdate(book);

        Library library = libraryRepo.get(libraryId);
        String bookId = UUID.randomUUID().toString();

        LibraryStorage libraryStorage = new LibraryStorage(bookId, library, book, true);
        HibernateAdapter.addObject(libraryStorage);

        return bookId;
    }

    @Override
    public void deleteBookFromLibrary(String bookId) throws BookAlreadyDeletedException, LibraryStorageNotFoundException {
        try {
            LibraryStorage storage = getStorageByBookId(bookId);
            Book book = storage.getBook();
            Library library = storage.getLibrary();

            LibraryStorage libraryStorage = new LibraryStorage(bookId, library, book, true);
            HibernateAdapter.deleteObject(libraryStorage);
        } catch (javax.persistence.NoResultException e) {
            throw new BookAlreadyDeletedException("Book already deleted from library");
        }

    }

    @Override
    public Book getBookByBookId(String bookId) throws BookRepository.BookNotFoundException {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            Book book = (Book) session.createQuery("select new model.Book(storage.book.isbn, storage.book.title, storage.book.author, storage.book.year, storage.book.category) " +
                    "FROM LibraryStorage as storage " +
                    "where storage.bookid like :bookid")
                    .setParameter("bookid", bookId)
                    .getSingleResult();
            tx.commit();
            return book;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            throw new BookRepository.BookNotFoundException("There is no book with this book id: " + bookId);
        }
    }

    @Override
    public List<Book> search(String libraryId, String searchTerm) {
        if(searchTerm.equals(""))
            return Collections.emptyList();

        List<LibraryStorage> list = HibernateAdapter.searchInLibrary(searchTerm, libraryId);
        return list.stream().map(LibraryStorage::getBook).collect(Collectors.toList());
    }

    @Override
    public List<Book> advancedSearch(String libraryId, String isbn, String title, String author, int year, Book.Category category) {
        String noMatchString = "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz";
        if (isbn.equals(""))
            isbn = noMatchString;
        if (title.equals(""))
            title = noMatchString;
        if (author.equals(""))
            author = noMatchString;

        List<LibraryStorage> list = HibernateAdapter.advancedSearchInLibrary(libraryId, isbn, title, author, year,category);
        return list.stream().map(LibraryStorage::getBook).collect(Collectors.toList());
    }

    @Override
    public List<LibraryStorage> getStoragesByIsbnAndLibrary(String isbn, String libraryId) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            List<LibraryStorage> storages = session.createQuery("FROM LibraryStorage as s where " +
                    "s.book.isbn like :isbn and " +
                    "s.library.libraryID like :libraryid")
                    .setParameter("isbn", isbn)
                    .setParameter("libraryid", libraryId)
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
    public List<LibraryStorage> getLibrariesStorageByIsbn(String isbn) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            List<LibraryStorage> storages = session.createQuery("FROM LibraryStorage as s where s.book.isbn like :isbn")
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
    public List<String> getAvailableBooks(String isbn, String libraryID) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            List<String> ids = session.createQuery("select s.bookid FROM LibraryStorage as s where " +
                    "s.book.isbn like :isbn and " +
                    "s.library.libraryID like :libraryid and " +
                    "s.available = TRUE ")
                    .setParameter("isbn", isbn)
                    .setParameter("libraryid", libraryID)
                    .list();
            tx.commit();
            return ids;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return new LinkedList<>();
    }

    @Override
    public LibraryStorage getStorageByBookId(String bookId) throws LibraryStorageNotFoundException {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            LibraryStorage libraryStorage
                    = (LibraryStorage) session.createQuery("FROM LibraryStorage as s where " +
                    "s.bookid like :bookId")
                    .setParameter("bookId", bookId)
                    .getSingleResult();
            tx.commit();
            return libraryStorage;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        throw new LibraryStorageNotFoundException("There is no library storage with bookId: " + bookId);
    }

    public class BookAlreadyDeletedException extends Exception {
        public BookAlreadyDeletedException(String msg) {
            super(msg);
        }
    }

    public class LibraryStorageNotFoundException extends Exception{
        public LibraryStorageNotFoundException(String msg) {
            super(msg);
        }
    }
}

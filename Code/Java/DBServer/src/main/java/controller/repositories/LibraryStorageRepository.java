package controller.repositories;

import controller.HibernateAdapter;
import model.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class LibraryStorageRepository implements LibraryStorageRepo {

    private static LibraryStorageRepository instance;
    private SessionFactory sessionFactory;
    private LibraryRepo libraryRepo;
    private BookRepo bookRepo;

    public LibraryStorageRepository() {
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
        LibraryStorageID id = new LibraryStorageID(book, library, bookId);

        LibraryStorage libraryStorage = new LibraryStorage(id, true);
        HibernateAdapter.addObject(libraryStorage);

        return bookId;
    }

    @Override
    public void deleteBookFromLibrary(String bookId, String libraryId) throws LibraryRepository.LibraryNotFoundException, BookRepository.BookNotFoundException, BookAlreadyDeletedException {
        try {
            Book book = getBookByBookId(bookId);
            Library library = libraryRepo.get(libraryId);
            LibraryStorageID id = new LibraryStorageID(book, library, bookId);

            LibraryStorage libraryStorage = new LibraryStorage(id, true);
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
            Book book = (Book) session.createQuery("select new model.Book(storage.id.book.isbn, storage.id.book.title, storage.id.book.author, storage.id.book.year, storage.id.book.category) " +
                    "FROM LibraryStorage as storage " +
                    "where storage.id.bookid like :bookid")
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
        final String emptyStringValue = "!@#$%^&*()"; //this value represents empty string for query so that it is not matched to any typical string value
        if (searchTerm.equals(""))
            searchTerm = emptyStringValue;

        int year;
        try {
            year = Integer.parseInt(searchTerm);
        } catch (NumberFormatException e) {
            year = 0;
        }

        String cat = searchTerm.toLowerCase();
        cat = cat.substring(0, 1).toUpperCase() + cat.substring(1);
        Book.Category searchCategory;
        try {
            searchCategory = Book.Category.valueOf(cat);
        } catch (IllegalArgumentException e) {
            searchCategory = Book.Category.Empty;
        }

        return advancedSearch(libraryId, searchTerm, searchTerm, searchTerm, year, searchCategory);
    }

    @Override
    public List<Book> advancedSearch(String libraryId, String isbn, String title, String author, int year, Book.Category category) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
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

    @Override
    public List<LibraryStorage> getStoragesByIsbnAndLibrary(String isbn, String libraryId) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            List<LibraryStorage> storages = session.createQuery("FROM LibraryStorage as s where " +
                    "s.id.book.isbn like :isbn and " +
                    "s.id.library.libraryID like :libraryid")
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
            List<LibraryStorage> storages = session.createQuery("FROM LibraryStorage as s where s.id.book.isbn like :isbn")
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

    public class BookAlreadyDeletedException extends Throwable {
        public BookAlreadyDeletedException(String msg) {
            super(msg);
        }
    }
}

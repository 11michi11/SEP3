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

    private SessionFactory sessionFactory;
    private LibraryRepo libraryRepo;
    private BookRepo bookRepo;

    public LibraryStorageRepository() {
        this.sessionFactory = HibernateAdapter.getSessionFactory();
        this.bookRepo = BookRepository.getInstance();
    }


    @Override
    public void addBookToLibrary(Book book, String libraryId) throws LibraryRepository.LibraryNotFoundException {
        //Save book to Book table
        bookRepo.saveOrUpdate(book);

        Library library = libraryRepo.get(libraryId);
        String bookId = UUID.randomUUID().toString();
        LibraryStorageID id = new LibraryStorageID(book, library, bookId);

        LibraryStorage libraryStorage = new LibraryStorage(id, true);
        HibernateAdapter.addObject(libraryStorage);
    }

    @Override
    public void deleteBookFromLibrary(String bookId, String libraryId) throws LibraryRepository.LibraryNotFoundException, BookRepository.BookNotFoundException {
        Book book = getBookByBookId(bookId);

        Library library = libraryRepo.get(libraryId);
        LibraryStorageID id = new LibraryStorageID(book, library, bookId);

        LibraryStorage libraryStorage = new LibraryStorage(id, true);
        HibernateAdapter.deleteObject(libraryStorage);
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
    public List<LibraryBook> search(String libraryId, String searchTerm) {
        return null;
    }

    @Override
    public List<LibraryBook> advancedSearch(String libraryId, String isbn, String title, String author, int year, Book.Category category) {
        return null;
    }

    @Override
    public List<LibraryStorage> getStoragesByIsbnAndLibrary(String isbn, String libraryId) {
        return null;
    }
}

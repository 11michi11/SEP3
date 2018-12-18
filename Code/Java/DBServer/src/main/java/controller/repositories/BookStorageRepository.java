package controller.repositories;

import controller.HibernateAdapter;
import model.*;
import org.hibernate.SessionFactory;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BookStorageRepository implements BookStorageRepo {

    private static BookStorageRepository instance;
    private SessionFactory sessionFactory;
    private LibraryStorageRepo libraryStorageRepo;
    private BookStoreStorageRepo bookStoreStorageRepo;
    private BookRepo bookRepo;

    public BookStorageRepository() {
        this.sessionFactory = HibernateAdapter.getSessionFactory();
        this.libraryStorageRepo = LibraryStorageRepository.getInstance();
        this.bookStoreStorageRepo = BookStoreStorageRepository.getInstance();
        this.bookRepo = BookRepository.getInstance();
    }

    public static BookStorageRepository getInstance() {
        if (instance == null) {
            instance = new BookStorageRepository();
        }
        return instance;
    }

    @Override
    public DetailedBook getBookDetails(String isbn) throws BookRepository.BookNotFoundException {
        List<LibraryStorage> libraryStorages = libraryStorageRepo.getLibrariesStorageByIsbn(isbn);
        List<BookStoreStorage> bookStoreStorages = bookStoreStorageRepo.getBookStoresStorageByIsbn(isbn);

        libraryStorages.forEach(System.out::println);

        Book book;
        try {
            //There is only one book
            book = libraryStorages.get(0).getBook();
        } catch (IndexOutOfBoundsException e) {
            book = bookRepo.get(isbn);
            libraryStorages = Collections.emptyList();
        }

        Book finalBook = book;
        List<BookStore> bookStores = bookStoreStorages.stream()
                .filter(libraryStorage -> libraryStorage.getBook().getIsbn().equals(finalBook.getIsbn()))
                .map(BookStoreStorage::getBookstore).collect(Collectors.toList());

        return new DetailedBook(book, libraryStorages, bookStores);
    }
}

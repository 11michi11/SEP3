package controller.repositories;

import controller.HibernateAdapter;
import model.*;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.stream.Collectors;

public class BookStorageRepository implements BookStorageRepo {

    private static BookStorageRepository instance;
    private SessionFactory sessionFactory;
    private LibraryStorageRepo libraryStorageRepo;
    private BookStoreStorageRepo bookStoreStorageRepo;

    public BookStorageRepository() {
        this.sessionFactory = HibernateAdapter.getSessionFactory();
        this.libraryStorageRepo = LibraryStorageRepository.getInstance();
        this.bookStoreStorageRepo = BookStoreStorageRepository.getInstance();
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

        //There is only one book
        try {
            Book book = libraryStorages.get(0).getBook();

            List<BookStore> bookStores = bookStoreStorages.stream()
                    .filter(libraryStorage -> libraryStorage.getId().getBook().getIsbn().equals(book.getIsbn()))
                    .map(libraryStorage -> libraryStorage.getId().getBookstore()).collect(Collectors.toList());

            return new DetailedBook(book, libraryStorages, bookStores);
        } catch (IndexOutOfBoundsException e) {
            throw new BookRepository.BookNotFoundException("There is no book with isbn:" + isbn);
        }
    }
}

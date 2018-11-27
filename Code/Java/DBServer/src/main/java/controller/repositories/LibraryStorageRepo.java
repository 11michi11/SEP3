package controller.repositories;

import model.Book;
import model.LibraryStorage;

import java.util.List;

public interface LibraryStorageRepo {

    String addBookToLibrary(Book book, String libraryId) throws LibraryRepository.LibraryNotFoundException;

    void deleteBookFromLibrary(String bookId) throws LibraryRepository.LibraryNotFoundException, BookRepository.BookNotFoundException, LibraryStorageRepository.BookAlreadyDeletedException, LibraryStorageRepository.LibraryStorageNotFoundException;

    Book getBookByBookId(String bookId) throws BookRepository.BookNotFoundException;

    List<Book> search(String libraryId, String searchTerm);

    List<Book> advancedSearch(String libraryId, String isbn, String title, String author, int year, Book.Category category);

    List<LibraryStorage> getStoragesByIsbnAndLibrary(String isbn, String libraryId);

    List<LibraryStorage> getLibrariesStorageByIsbn(String isbn);

    List<String> getAvailableBooks(String isbn, String libraryID);

    LibraryStorage getStorageByBookId(String bookId) throws LibraryStorageRepository.LibraryStorageNotFoundException;
}

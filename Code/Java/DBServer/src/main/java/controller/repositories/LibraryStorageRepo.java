package controller.repositories;

import model.Book;
import model.LibraryBook;
import model.LibraryStorage;

import java.util.List;

public interface LibraryStorageRepo {

    void addBookToLibrary(Book book, String libraryId) throws LibraryRepository.LibraryNotFoundException;

    void deleteBookFromLibrary(String bookId, String libraryId) throws LibraryRepository.LibraryNotFoundException, BookRepository.BookNotFoundException;

    Book getBookByBookId(String bookId) throws BookRepository.BookNotFoundException;

    List<LibraryBook> search(String libraryId, String searchTerm);

    List<LibraryBook> advancedSearch(String libraryId, String isbn, String title, String author, int year, Book.Category category);

    List<LibraryStorage> getStoragesByIsbnAndLibrary(String isbn, String libraryId);
}

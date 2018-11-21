import controller.repositories.*;
import model.Book;
import model.BookStoreStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


public class BookStoreStorageRepoTest {

	private BookRepo bookRepo;
	private BookStoreStorageRepo bookStoreStorageRepo;

	private static final String BOOKSTORE_ID= "eb3777c8-77fe-4acd-962d-6853da2e05e0";

	@BeforeEach
	public void setUp() {
		bookRepo = BookRepository.getInstance();
		bookStoreStorageRepo = BookStoreStorageRepository.getInstance();
	}

	@Test
	public void addBookToBookStore() {
		Book book = new Book("isbn", "title", "author",0,Book.Category.Empty);

		try {
			bookStoreStorageRepo.addBookToBookStore(book, BOOKSTORE_ID);

			bookStoreStorageRepo.deleteBookFromBookStore(book.getIsbn(), BOOKSTORE_ID);
		} catch (BookStoreRepository.BookStoreNotFoundException e) {
			fail("No Bookstore");
		} catch (BookRepository.BookNotFoundException e) {
			fail("There is not such a book");
		}
	}

	@Test
	public void addBookToBookStoreWhenBookAlreadyExistsTest() {
		Book book = new Book("isbn", "title", "author",0,Book.Category.Empty);
		bookRepo.add(book);

		try {
			bookStoreStorageRepo.addBookToBookStore(book, BOOKSTORE_ID);
			bookStoreStorageRepo.deleteBookFromBookStore(book.getIsbn(), BOOKSTORE_ID);
			bookRepo.delete("isbn");
		} catch (BookStoreRepository.BookStoreNotFoundException e) {
			fail("No Bookstore");
		} catch (BookRepository.BookNotFoundException e) {
			e.printStackTrace();
			fail("There is not such a book");
		}
	}

	@Test
	public void getBookStoresStoragesByIdTest() {
		Book book = new Book("isbn", "title", "author",0,Book.Category.Empty);
		bookRepo.add(book);


		try {
			bookStoreStorageRepo.addBookToBookStore(book, BOOKSTORE_ID);
			//assertEquals(, bookStoreStorageRepo.getBookStoresStorageByIsbn("isbn"));

			bookStoreStorageRepo.deleteBookFromBookStore(book.getIsbn(), BOOKSTORE_ID);
			bookRepo.delete("isbn");
		} catch (BookStoreRepository.BookStoreNotFoundException e) {
			fail("No Bookstore");
		} catch (BookRepository.BookNotFoundException e) {
			fail("There is no such a book");
		}


	}

	@Test
	public void search() {
		Book book = new Book("isbn", "title", "author",0,Book.Category.Empty);
		List<Book> books = Collections.singletonList(book);
		bookRepo.add(book);

		try {
			bookStoreStorageRepo.addBookToBookStore(book, BOOKSTORE_ID);

		} catch (BookStoreRepository.BookStoreNotFoundException e) {
			fail("No Bookstore");
		}
		assertEquals(books , bookStoreStorageRepo.search("isbn"));
		assertEquals(books , bookStoreStorageRepo.search("book"));
		assertEquals(books , bookStoreStorageRepo.search("author"));
		assertEquals(books , bookStoreStorageRepo.search("0"));
		assertEquals(books , bookStoreStorageRepo.search("Empty"));
		try {
			bookStoreStorageRepo.deleteBookFromBookStore(book.getIsbn(), BOOKSTORE_ID);
			bookRepo.delete("isbn");
		} catch (BookRepository.BookNotFoundException e) {
			fail("No Bookstore");
		} catch (BookStoreRepository.BookStoreNotFoundException e) {
			fail("There is no such a book");
		}
	}

	@Test
	public void advancedSearch() {
		Book book = new Book("isbn", "title", "author",0,Book.Category.Empty);
		List<Book> books = Collections.singletonList(book);
		bookRepo.add(book);

		try {
			bookStoreStorageRepo.addBookToBookStore(book, BOOKSTORE_ID);

		} catch (BookStoreRepository.BookStoreNotFoundException e) {
			fail("No Bookstore");
		}
		assertEquals(books , bookStoreStorageRepo.advancedSearch("", "isbn", "title", "author", 0, Book.Category.Empty));
		try {
			bookStoreStorageRepo.deleteBookFromBookStore(book.getIsbn(), BOOKSTORE_ID);
			bookRepo.delete("isbn");
		} catch (BookRepository.BookNotFoundException e) {
			fail("No Bookstore");
		} catch (BookStoreRepository.BookStoreNotFoundException e) {
			fail("There is no such a book");
		}
	}
}

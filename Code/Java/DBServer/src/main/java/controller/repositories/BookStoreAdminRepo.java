package controller.repositories;

import model.BookStoreAdmin;

public interface BookStoreAdminRepo {

	void add(BookStoreAdmin admin);
	void delete(BookStoreAdmin admin);
	BookStoreAdmin getBookstoreAdmin(String id) throws BookStoreAdminRepository.BookStoreAdminNotFoundException;
}

package controller.repositories;

import model.BookStoreAdmin;

public interface BookStoreAdminRepo {

    void add(BookStoreAdmin admin);

    void delete(BookStoreAdmin admin);

    BookStoreAdmin get(String id) throws BookStoreAdminRepository.BookStoreAdminNotFoundException;

    BookStoreAdmin getByEmail(String email) throws BookStoreAdminRepository.BookStoreAdminNotFoundException;
}

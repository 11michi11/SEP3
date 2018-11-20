package controller.repositories;

import model.BookStore;

public interface BookStoreRepo {

    BookStore get(String bookStoreId) throws BookStoreRepository.BookStoreNotFoundException;

}

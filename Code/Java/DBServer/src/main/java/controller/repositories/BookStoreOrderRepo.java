package controller.repositories;

import model.BookStoreOrder;

import java.util.List;

public interface BookStoreOrderRepo {

    String add(String bookId, String customerId) throws BookStoreRepository.BookStoreNotFoundException, CustomerRepository.CustomerNotFoundException, BookStoreStorageRepository.BookStoreStorageNotFoundException;

    void delete(String orderId) throws CustomerRepository.CustomerNotFoundException;

    List<BookStoreOrder> get();

    BookStoreOrder get(String orderId);

}

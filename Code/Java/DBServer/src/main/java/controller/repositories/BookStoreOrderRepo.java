package controller.repositories;

import model.BookStoreOrder;
import model.BookStoreOrderData;

import java.util.List;

public interface BookStoreOrderRepo {

    String add(String isbn, String bookstoreId, String customerId) throws BookStoreRepository.BookStoreNotFoundException, CustomerRepository.CustomerNotFoundException, BookStoreStorageRepository.BookStoreStorageNotFoundException;

    void delete(String orderId) throws CustomerRepository.CustomerNotFoundException;

    List<BookStoreOrder> get();

    BookStoreOrder get(String orderId);

    List<BookStoreOrderData> getBookStoreOrders(String bookStoreId);
}

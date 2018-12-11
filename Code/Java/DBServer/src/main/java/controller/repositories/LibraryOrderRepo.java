package controller.repositories;

import model.LibraryOrder;
import model.LibraryOrderData;

import java.util.List;

public interface LibraryOrderRepo {

    String add(String bookId, String customerId) throws LibraryRepository.LibraryNotFoundException, LibraryStorageRepository.LibraryStorageNotFoundException, CustomerRepository.CustomerNotFoundException;

    void delete(String orderId) throws LibraryStorageRepository.LibraryStorageNotFoundException, CustomerRepository.CustomerNotFoundException;

    List<LibraryOrder> get();

    LibraryOrder get(String orderId);

    List<LibraryOrderData> getLibraryOrders(String libraryId);
}

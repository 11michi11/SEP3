package controller.repositories;

import model.LibraryOrder;

import java.util.List;

public interface LibraryOrderRepo {

    void add(String bookId, String libraryId, String customerId);

    List<LibraryOrder> get();

}

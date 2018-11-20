package controller.repositories;

import model.DetailedBook;

public interface BookStorageRepo {

    DetailedBook getBookDetails(String isbn);

}

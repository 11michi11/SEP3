package controller.repositories;

import model.Library;

public interface LibraryRepo {

    Library get(String libraryId) throws LibraryRepository.LibraryNotFoundException;

}

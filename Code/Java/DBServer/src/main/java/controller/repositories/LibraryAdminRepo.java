package controller.repositories;

import model.LibraryAdmin;

public interface LibraryAdminRepo {

    LibraryAdmin get(String id) throws LibraryAdminRepository.LibraryAdminNotFoundException;

    void add(LibraryAdmin admin);
    void delete(LibraryAdmin admin);

}

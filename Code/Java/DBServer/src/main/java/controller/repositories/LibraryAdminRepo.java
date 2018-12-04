package controller.repositories;

import model.LibraryAdmin;

public interface LibraryAdminRepo {

    LibraryAdmin get(String id) throws LibraryAdminRepository.LibraryAdminNotFoundException;

    void save(LibraryAdmin admin);

}

package controller.repositories;

import model.LibraryAdmin;

public interface LibraryAdminRepo {

    LibraryAdmin getLibraryAdmin(String id) throws LibraryAdminRepository.LibraryAdminNotFoundException;

    void add(LibraryAdmin admin);
    void delete(LibraryAdmin admin);

    LibraryAdmin getByEmail(String email) throws LibraryAdminRepository.LibraryAdminNotFoundException;
}

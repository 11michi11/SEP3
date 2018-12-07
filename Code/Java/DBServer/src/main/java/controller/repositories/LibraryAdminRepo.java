package controller.repositories;

import model.LibraryAdmin;

public interface LibraryAdminRepo {

    void add(LibraryAdmin admin);

    void delete(LibraryAdmin admin);

    LibraryAdmin get(String id) throws LibraryAdminRepository.LibraryAdminNotFoundException;

    LibraryAdmin getByEmail(String email) throws LibraryAdminRepository.LibraryAdminNotFoundException;
}

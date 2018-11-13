package model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "bookstorestorage")
public class BookStoreStorage {

    @EmbeddedId
    private BookStoreStorageID id;

    public BookStoreStorage() {
    }

    public BookStoreStorage(BookStoreStorageID id) {
        this.id = id;
    }

    public BookStoreStorageID getId() {
        return id;
    }

    public void setId(BookStoreStorageID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BookStoreStorage{" +
                "id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookStoreStorage that = (BookStoreStorage) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}

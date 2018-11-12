package model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "librarystorage", schema = "public")
public class LibraryStorage {

    @EmbeddedId
    private LibraryStorageID id;

    @Column(name = "available")
    private boolean available;

    public LibraryStorage() {
    }

    public LibraryStorageID getId() {
        return id;
    }

    public void setId(LibraryStorageID id) {
        this.id = id;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "LibraryStorage{" +
                "id=" + id +
                ", available=" + available +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LibraryStorage that = (LibraryStorage) o;

        if (available != that.available) return false;
        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (available ? 1 : 0);
        return result;
    }
}

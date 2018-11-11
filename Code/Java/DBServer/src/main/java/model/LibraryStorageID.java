package model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class LibraryStorageID implements Serializable {

    @ManyToOne
    @JoinColumn(name = "libraryid")
    private Library libraryid;

    @ManyToOne
    @JoinColumn(name = "isbn")
    private Book isbn;

    public LibraryStorageID() {
    }

    public Library getLibraryid() {
        return libraryid;
    }

    public void setLibraryid(Library libraryid) {
        this.libraryid = libraryid;
    }

    public Book getIsbn() {
        return isbn;
    }

    public void setIsbn(Book isbn) {
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return "LibraryStorageID{" +
                "libraryid=" + libraryid +
                ", isbn=" + isbn +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LibraryStorageID that = (LibraryStorageID) o;

        if (libraryid != null ? !libraryid.equals(that.libraryid) : that.libraryid != null) return false;
        return isbn != null ? isbn.equals(that.isbn) : that.isbn == null;
    }

    @Override
    public int hashCode() {
        int result = libraryid != null ? libraryid.hashCode() : 0;
        result = 31 * result + (isbn != null ? isbn.hashCode() : 0);
        return result;
    }
}

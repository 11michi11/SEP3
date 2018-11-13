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
    private Library library;

    @ManyToOne
    @JoinColumn(name = "isbn")
    private Book book;

    @Column(name = "bookid")
    private String bookid;

    public LibraryStorageID() {
    }

    public LibraryStorageID(Book book, Library library, String bookid) {
        this.book = book;
        this.library = library;
        this.bookid = bookid;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library libraryid) {
        this.library = libraryid;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book isbn) {
        this.book = isbn;
    }

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    @Override
    public String toString() {
        return "LibraryStorageID{" +
                "library=" + library +
                ", book=" + book +
                ", bookid='" + bookid + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LibraryStorageID that = (LibraryStorageID) o;

        if (library != null ? !library.equals(that.library) : that.library != null) return false;
        return book != null ? book.equals(that.book) : that.book == null;
    }

    @Override
    public int hashCode() {
        int result = library != null ? library.hashCode() : 0;
        result = 31 * result + (book != null ? book.hashCode() : 0);
        return result;
    }


}

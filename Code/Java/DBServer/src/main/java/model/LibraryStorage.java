package model;

import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import javax.persistence.*;

@Entity
@Table(name = "librarystorage", schema = "public")
@Indexed
public class LibraryStorage {

    @Id
    @Column(name = "bookid")
    @Field(name = "id")
    private String bookid;

    @ManyToOne
    @JoinColumn(name = "libraryid")
    @IndexedEmbedded(depth = 1)
    private Library library;

    @ManyToOne
    @JoinColumn(name = "isbn")
    @IndexedEmbedded(depth = 1)
    private Book book;

    @Column(name = "available")
    private boolean available;

    public LibraryStorage() {
    }

    public LibraryStorage(String bookid, Library library, Book book, boolean available) {
        this.bookid = bookid;
        this.library = library;
        this.book = book;
        this.available = available;
    }

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
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
                "bookid='" + bookid + '\'' +
                ", library=" + library +
                ", book=" + book +
                ", available=" + available +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LibraryStorage that = (LibraryStorage) o;

        if (available != that.available) return false;
        if (bookid != null ? !bookid.equals(that.bookid) : that.bookid != null) return false;
        if (library != null ? !library.equals(that.library) : that.library != null) return false;
        return book != null ? book.equals(that.book) : that.book == null;
    }

    @Override
    public int hashCode() {
        int result = bookid != null ? bookid.hashCode() : 0;
        result = 31 * result + (library != null ? library.hashCode() : 0);
        result = 31 * result + (book != null ? book.hashCode() : 0);
        result = 31 * result + (available ? 1 : 0);
        return result;
    }
}

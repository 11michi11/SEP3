package model;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class LibraryOrderID implements Serializable {

    @ManyToOne
    @JoinColumn(name = "libraryid")
    private Library library;

    @ManyToOne
    @JoinColumn(name = "isbn")
    private Book book;

    @ManyToOne
    @PrimaryKeyJoinColumns({
            @PrimaryKeyJoinColumn(name = "bookid"),
            @PrimaryKeyJoinColumn(name = "libraryid")
    })
    private LibraryStorage libraryBook;

//    @PrimaryKeyJoinColumn(name = "isbn")
    @ManyToOne
    @JoinColumn(name = "customerid")
    private Customer customer;

    public LibraryOrderID() {
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

    public LibraryStorage getLibraryBook() {
        return libraryBook;
    }

    public void setLibraryBook(LibraryStorage libraryBook) {
        this.libraryBook = libraryBook;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "LibraryOrderID{" +
                "library=" + library +
                ", book=" + book +
                ", libraryBook=" + libraryBook +
                ", customer=" + customer +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LibraryOrderID that = (LibraryOrderID) o;

        if (library != null ? !library.equals(that.library) : that.library != null) return false;
        if (book != null ? !book.equals(that.book) : that.book != null) return false;
        if (libraryBook != null ? !libraryBook.equals(that.libraryBook) : that.libraryBook != null) return false;
        return customer != null ? customer.equals(that.customer) : that.customer == null;
    }

    @Override
    public int hashCode() {
        int result = library != null ? library.hashCode() : 0;
        result = 31 * result + (book != null ? book.hashCode() : 0);
        result = 31 * result + (libraryBook != null ? libraryBook.hashCode() : 0);
        result = 31 * result + (customer != null ? customer.hashCode() : 0);
        return result;
    }
}

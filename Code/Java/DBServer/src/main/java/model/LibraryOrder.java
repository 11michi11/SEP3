package model;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "libraryorder", schema = "public")
public class  LibraryOrder {

    @Id @Column(name = "orderid")
    private String orderid;

    @ManyToOne
    @JoinColumn(name = "libraryid")
    private Library library;

    @ManyToOne
    @JoinColumn(name = "isbn")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "customerid")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "bookid")
    private LibraryStorage libraryStorage;


    @Column(name = "dateoforder")
    @Temporal(TemporalType.DATE)
    private Calendar dateOfOrder;

    @Column(name = "returndate")
    @Temporal(TemporalType.DATE)
    private Calendar returnDate;

    public LibraryOrder() {
    }

    public LibraryOrder(String orderid, LibraryStorage libraryStorage, Library library, Book book, Customer customer, Calendar dateOfOrder, Calendar returnDate) {
        this.orderid = orderid;
        this.libraryStorage = libraryStorage;
        this.library = library;
        this.book = book;
        this.customer = customer;
        this.dateOfOrder = dateOfOrder;
        this.returnDate = returnDate;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Calendar getDateOfOrder() {
        return dateOfOrder;
    }

    public void setDateOfOrder(Calendar dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
    }

    public Calendar getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Calendar returnDate) {
        this.returnDate = returnDate;
    }

    public LibraryStorage getLibraryStorage() {
        return libraryStorage;
    }

    public void setLibraryStorage(LibraryStorage libraryStorage) {
        this.libraryStorage = libraryStorage;
    }

    @Override
    public String toString() {
        return "LibraryOrder{" +
                "orderid='" + orderid + '\'' +
                ", library=" + library +
                ", book=" + book +
                ", customer=" + customer +
                ", bookId=" + libraryStorage.getBookid() +
                ", dateOfOrder=" + dateOfOrder.getTime() +
                ", returnDate=" + returnDate.getTime() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LibraryOrder that = (LibraryOrder) o;

        if (orderid != null ? !orderid.equals(that.orderid) : that.orderid != null) return false;
        if (library != null ? !library.equals(that.library) : that.library != null) return false;
        if (book != null ? !book.equals(that.book) : that.book != null) return false;
        if (customer != null ? !customer.equals(that.customer) : that.customer != null) return false;
        if (libraryStorage != null ? !libraryStorage.equals(that.libraryStorage) : that.libraryStorage != null)
            return false;
        if (dateOfOrder != null ? !dateOfOrder.equals(that.dateOfOrder) : that.dateOfOrder != null) return false;
        return returnDate != null ? returnDate.equals(that.returnDate) : that.returnDate == null;
    }

    @Override
    public int hashCode() {
        int result = orderid != null ? orderid.hashCode() : 0;
        result = 31 * result + (library != null ? library.hashCode() : 0);
        result = 31 * result + (book != null ? book.hashCode() : 0);
        result = 31 * result + (customer != null ? customer.hashCode() : 0);
        result = 31 * result + (libraryStorage != null ? libraryStorage.hashCode() : 0);
        result = 31 * result + (dateOfOrder != null ? dateOfOrder.hashCode() : 0);
        result = 31 * result + (returnDate != null ? returnDate.hashCode() : 0);
        return result;
    }
}

package model;

import javax.persistence.*;

@Entity
@Table(name = "bookstoreorder", schema = "public")
public class BookStoreOrder {

    @Id @Column(name = "orderid")
    private String orderid;

    @ManyToOne
    @JoinColumn(name = "bookstoreid")
    private BookStore bookStore;

    @ManyToOne
    @JoinColumn(name = "isbn")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "customerid")
    private Customer customer;

    public BookStoreOrder() {
    }

    public BookStoreOrder(String orderid, BookStore bookStore, Book book, Customer customer) {
        this.orderid = orderid;
        this.bookStore = bookStore;
        this.book = book;
        this.customer = customer;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public BookStore getBookStore() {
        return bookStore;
    }

    public void setBookStore(BookStore bookStore) {
        this.bookStore = bookStore;
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

    @Override
    public String toString() {
        return "BookStoreOrder{" +
                "orderid='" + orderid + '\'' +
                ", bookStore=" + bookStore +
                ", book=" + book +
                ", customer=" + customer +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookStoreOrder that = (BookStoreOrder) o;

        if (orderid != null ? !orderid.equals(that.orderid) : that.orderid != null) return false;
        if (bookStore != null ? !bookStore.equals(that.bookStore) : that.bookStore != null) return false;
        if (book != null ? !book.equals(that.book) : that.book != null) return false;
        return customer != null ? customer.equals(that.customer) : that.customer == null;
    }

    @Override
    public int hashCode() {
        int result = orderid != null ? orderid.hashCode() : 0;
        result = 31 * result + (bookStore != null ? bookStore.hashCode() : 0);
        result = 31 * result + (book != null ? book.hashCode() : 0);
        result = 31 * result + (customer != null ? customer.hashCode() : 0);
        return result;
    }
}

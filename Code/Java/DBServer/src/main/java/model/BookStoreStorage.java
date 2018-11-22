package model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "bookstorestorage")
public class BookStoreStorage implements Serializable {

    @ManyToOne
    @JoinColumn(name = "bookstoreid")
    private BookStore bookstore;

    @Id @ManyToOne
    @JoinColumn(name = "isbn")
    private Book book;

    public BookStoreStorage() {
    }

    public BookStoreStorage(BookStore bookstore, Book book) {
        this.bookstore = bookstore;
        this.book = book;
    }

    public BookStore getBookstore() {
        return bookstore;
    }

    public void setBookstore(BookStore bookstore) {
        this.bookstore = bookstore;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "BookStoreStorage{" +
                ", bookstore=" + bookstore +
                ", book=" + book +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookStoreStorage that = (BookStoreStorage) o;

        if (bookstore != null ? !bookstore.equals(that.bookstore) : that.bookstore != null) return false;
        return book != null ? book.equals(that.book) : that.book == null;
    }

    @Override
    public int hashCode() {
        int result = 31 * (bookstore != null ? bookstore.hashCode() : 0);
        result = 31 * result + (book != null ? book.hashCode() : 0);
        return result;
    }
}

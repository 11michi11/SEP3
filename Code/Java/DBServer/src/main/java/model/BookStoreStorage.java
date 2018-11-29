package model;

import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import javax.persistence.*;
import java.io.Serializable;

@Indexed
@Entity
@Table(name = "bookstorestorage")
public class BookStoreStorage implements Serializable {

    @Id @Column(name = "bookid")
    private String bookid;

    @ManyToOne
    @JoinColumn(name = "bookstoreid")
    @IndexedEmbedded(depth = 1)
    private BookStore bookstore;

    @ManyToOne
    @JoinColumn(name = "isbn")
    @IndexedEmbedded(depth = 1)
    @FieldBridge(impl= BookBridge.class)
    private Book book;

    public BookStoreStorage() {
    }

    public BookStoreStorage(String bookid,BookStore bookstore, Book book) {
        this.bookid = bookid;
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

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
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

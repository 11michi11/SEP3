package model;


import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class BookStoreStorageID implements Serializable {

    @ManyToOne
    @JoinColumn(name = "bookstoreid")
    private BookStore bookstore;

    @ManyToOne
    @JoinColumn(name = "isbn")
    private Book book;

    public BookStoreStorageID() {
    }

    public BookStore getBookstore() {
        return bookstore;
    }

    public void setBookstore(BookStore bookstoreid) {
        this.bookstore = bookstoreid;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "BookStoreStorageID{" +
                "bookstoreid=" + bookstore +
                ", book=" + book +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookStoreStorageID that = (BookStoreStorageID) o;

        if (bookstore != null ? !bookstore.equals(that.bookstore) : that.bookstore != null) return false;
        return book != null ? book.equals(that.book) : that.book == null;
    }

    @Override
    public int hashCode() {
        int result = bookstore != null ? bookstore.hashCode() : 0;
        result = 31 * result + (book != null ? book.hashCode() : 0);
        return result;
    }
}

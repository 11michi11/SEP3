package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bookstore")
public class BookStore {

    @Id @Column(name = "bookstoreid")
    private String bookstoreid;

    public BookStore() {
    }

    public String getBookstoreid() {
        return bookstoreid;
    }

    public void setBookstoreid(String bookstoreid) {
        this.bookstoreid = bookstoreid;
    }

    @Override
    public String toString() {
        return "BookStore{" +
                "bookstoreid='" + bookstoreid + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookStore bookStore = (BookStore) o;

        return bookstoreid != null ? bookstoreid.equals(bookStore.bookstoreid) : bookStore.bookstoreid == null;
    }

    @Override
    public int hashCode() {
        return bookstoreid != null ? bookstoreid.hashCode() : 0;
    }
}

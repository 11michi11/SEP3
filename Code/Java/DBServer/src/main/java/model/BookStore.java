package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bookstore")
public class BookStore implements Institution {

    @Id @Column(name = "bookstoreid")
    private String bookstoreid;

    @Column(name = "name")
    private String name;

    public BookStore() {
    }

    public BookStore(String bookstoreid) {
        this.bookstoreid = bookstoreid;
        this.name = "First Book Store";
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
                ", name='" + name + '\'' +
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

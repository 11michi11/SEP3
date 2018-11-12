package model;

public class BookStore implements Institution {

    private String bookstoreid;

    public BookStore(String bookstoreid) {
        this.bookstoreid = bookstoreid;
    }


    public String getBookstoreid() {
        return bookstoreid;
    }
}

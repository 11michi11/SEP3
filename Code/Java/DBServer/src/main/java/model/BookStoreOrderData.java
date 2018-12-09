package model;

import java.util.Calendar;

public class BookStoreOrderData {
    private String orderId;
    private String isbn;
    private String bookName;
    private String customerName;
    private String customerEmail;

    public BookStoreOrderData(String orderId, String isbn, String bookName, String customerName, String customerEmail) {
        this.orderId = orderId;
        this.isbn = isbn;
        this.bookName = bookName;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
    }


}

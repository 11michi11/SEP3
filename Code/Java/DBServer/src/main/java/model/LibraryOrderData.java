package model;

import java.util.Calendar;

public class LibraryOrderData {

    private String orderId;
    private String isbn;
    private String bookName;
    private String customerName;
    private String customerEmail;
    private Calendar dateOfOrder;
    private Calendar returnDate;

    public LibraryOrderData(String orderId, String isbn, String bookName, String customerName, String customerEmail, Calendar dateOfOrder, Calendar returnDate) {
        this.orderId = orderId;
        this.isbn = isbn;
        this.bookName = bookName;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.dateOfOrder = dateOfOrder;
        this.returnDate = returnDate;
    }
}

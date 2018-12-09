package model;

import java.util.Calendar;

public class LibraryOrderData {

    private String orderId;
    private String isbn;
    private String bookName;
    private Calendar dateOfOrder;
    private Calendar returnDate;
    private String customerName;
    private String customerEmail;
    private String customerAddress;
    private int customerPhoneNum;

    public LibraryOrderData(String orderId, String isbn, String bookName, Calendar dateOfOrder, Calendar returnDate,  String customerName, String customerEmail, String customerAddress, int customerPhoneNum) {
        this.orderId = orderId;
        this.isbn = isbn;
        this.bookName = bookName;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.dateOfOrder = dateOfOrder;
        this.returnDate = returnDate;
        this.customerAddress = customerAddress;
        this.customerPhoneNum = customerPhoneNum;
    }
}

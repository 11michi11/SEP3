package controller;

import controller.connection.DatabaseConnection;
import controller.connection.DatabaseProxy;
import model.Book;
import model.Customer;
import model.LogInResponse;
import model.AuthenticationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import utils.Password;

import java.util.List;

@Component
public class Controller {

    private DatabaseProxy db;

    @Autowired
    private SessionKeyManager sessionKeyManager;

    @Autowired
    Controller(DatabaseProxy db) {
        this.db = db;
    }

    public List<Book> search(String searchTerm) throws DatabaseConnection.ServerOfflineException, DatabaseConnection.SearchException {
        return db.search(searchTerm);
    }

    public List<Book> advancedSearch(String title, String author, int year, String isbn, Book.Category category) throws DatabaseConnection.ServerOfflineException, DatabaseConnection.SearchException {
        return db.advancedSearch(title, author, year, isbn, category);
    }

    public String getBookDetails(String isbn) {
        return db.getBookDetails(isbn);
    }

    public String addCustomer(Customer customer) throws DatabaseConnection.RegisterEmailException {
        customer.setPassword(Password.encryptPwd(customer.getPassword()));
        return db.addCustomer(customer);
    }

    public static void main(String[] args) {
        DatabaseConnection db = new DatabaseConnection();
        Controller controller = new Controller(db);
       System.out.println(controller.getBookDetails("978-83-8116-1"));
    }

	public String borrowBook(String isbn, String libraryID, String customerID) {
	    return db.borrowBook(isbn, libraryID, customerID);
    }

    public String buyBook(String isbn,String bookstoreID, String customerID) {
        return db.buyBook(isbn, bookstoreID, customerID);
    }

	public LogInResponse logIn(String email, String password) throws DatabaseConnection.LoginException {
        LogInResponse logInResponse = db.logIn(email,Password.encryptPwd(password));
        String institutionId = logInResponse.getInstitutionId();
        String userType = logInResponse.getUserType();
        logInResponse.setSessionKey(sessionKeyManager.generateSK(institutionId, AuthenticationData.UserType.valueOf(userType)));
        return logInResponse;
	}
}

package controller;

import model.Book;

import java.util.List;

public class Controller {

    private DBProxy db;

    private Controller(DBProxy db){
        this.db = db;
    }


    public static void main(String[] args) {
        DBProxy db = new HibernateAdapter();
        Controller controller = new Controller(db);
        controller.getAllBooks().forEach(System.out::println);
    }

    public List<Book> getAllBooks() {
        return db.getAllBooks();
    }


}

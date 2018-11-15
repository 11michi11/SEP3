package controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookstoreMain {
    public static void main(String[] args) {
        SpringApplication.run(BookstoreMain.class, args);

        Controller controller = new Controller(db);
//        System.out.println(controller.getBookDetails("978-83-8116-1"));
    }
}

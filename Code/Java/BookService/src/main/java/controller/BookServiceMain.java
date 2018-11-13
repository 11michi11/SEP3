package controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookServiceMain {
    public static void main(String[] args) {
        SpringApplication.run(BookServiceMain.class, args);

        Controller controller = new Controller();
    }
}

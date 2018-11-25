package controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BookstoreMain {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(BookstoreMain.class, args);

        Controller controller = context.getBean(Controller.class);
//        System.out.println(controller.getBookDetails("978-83-8116-1"));
    }
}

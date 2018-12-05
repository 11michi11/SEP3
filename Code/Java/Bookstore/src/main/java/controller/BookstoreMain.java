package controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BookstoreMain {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(BookstoreMain.class, args);

        Controller controller = context.getBean(Controller.class);

        SessionKeyManager sessionKeyManager = context.getBean(SessionKeyManager.class);
        try {
            System.out.println(sessionKeyManager.isSKValid("b0d934f2-c124-4392-8e01-7fe702027b1e"));
        } catch (SessionKeyManager.SessionKeyInvalidException e) {
            e.printStackTrace();
        }
    }
}

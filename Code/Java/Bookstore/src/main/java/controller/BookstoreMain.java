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
            System.out.println(sessionKeyManager.isSKValid("a168c083-c574-4113-ab99-1d82a3a1303c"));
        } catch (SessionKeyManager.SessionKeyInvalidException e) {
            e.printStackTrace();
        }
    }
}

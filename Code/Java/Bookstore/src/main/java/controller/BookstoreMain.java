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
            System.out.println(sessionKeyManager.isSKValid("38b848ca-7a46-4a42-968e-d2cc8694d1a0"));
        } catch (SessionKeyManager.SessionKeyInvalidException e) {
            e.printStackTrace();
        }
    }
}

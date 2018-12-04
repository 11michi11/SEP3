package controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication
public class BookServiceMain {
    public static void main(String[] args) {
        ConfigurableApplicationContext context= SpringApplication.run(BookServiceMain.class, args);

        Controller controller = context.getBean(Controller.class);
        System.out.println(SessionKeyManager.generateSK());
    }
}

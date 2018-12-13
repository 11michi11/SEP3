package controller.requests;

import controller.Controller;
import controller.SessionKeyManager;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookDetails implements ApplicationContextAware {

    private ConfigurableApplicationContext context;

    @CrossOrigin
    @GetMapping("/bookDetails/{isbn}")
    public String getBookDetails(@PathVariable String isbn){
        Controller controller=context.getBean(Controller.class);
        return controller.getBookDetails(isbn);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context=(ConfigurableApplicationContext) applicationContext;
    }
}

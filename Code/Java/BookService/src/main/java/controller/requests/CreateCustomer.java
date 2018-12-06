package controller.requests;

import controller.Controller;
import controller.SessionKeyManager;
import model.Customer;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.*;

@RestController
public class CreateCustomer implements ApplicationContextAware {

    private ConfigurableApplicationContext context;

    @RequestMapping(method= RequestMethod.POST, value="/signUp")
    public String addCustomer(@RequestBody Customer customer, @CookieValue("sessionKey") String sessionKey){
        SessionKeyManager.checkSessionKey(sessionKey);
        Controller controller=context.getBean(Controller.class);
        return controller.addCustomer(customer);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context=(ConfigurableApplicationContext) applicationContext;
    }
}

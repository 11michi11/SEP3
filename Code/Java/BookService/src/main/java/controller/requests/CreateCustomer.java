package controller.requests;

import controller.Controller;
import model.Customer;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateCustomer implements ApplicationContextAware {

    private ConfigurableApplicationContext context;

    @RequestMapping(method= RequestMethod.POST, value="/signUp")
    public String addCustomer(@RequestBody Customer customer){
        Controller controller=context.getBean(Controller.class);
        return controller.addCustomer(customer);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context=(ConfigurableApplicationContext) applicationContext;
    }
}

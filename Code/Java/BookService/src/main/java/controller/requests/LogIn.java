package controller.requests;

import controller.Controller;
import model.LogInResponse;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;

@RestController
public class LogIn implements ApplicationContextAware {

	private ConfigurableApplicationContext context;

	@CrossOrigin
	@GetMapping("/login")
	public LogInResponse logIn(@RequestBody String email,@RequestBody String password) throws LoginException {
		Controller controller = context.getBean(Controller.class);
		return controller.logIn(email,password);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context=(ConfigurableApplicationContext) applicationContext;
	}
}

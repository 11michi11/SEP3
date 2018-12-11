package controller.requests;

import controller.Controller;
import model.LogInResponse;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.*;



@RestController
public class LogIn implements ApplicationContextAware {

	private ConfigurableApplicationContext context;

	@CrossOrigin
	@PostMapping("/login")
	public LogInResponse logIn(@RequestBody LogInRequest logInData)  {
		Controller controller = context.getBean(Controller.class);
		return controller.logIn(logInData.email,logInData.password);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context=(ConfigurableApplicationContext) applicationContext;
	}

	public static class LogInRequest{
		public String email;
		public String password;

		public LogInRequest() {
		}

		public LogInRequest(String email, String password) {
			this.email = email;
			this.password = password;
		}
	}
}

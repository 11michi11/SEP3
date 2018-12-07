package controller.requests;

import controller.Controller;
import controller.SessionKeyManager;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.*;

@RestController
public class Order implements ApplicationContextAware {

	private ConfigurableApplicationContext context;
	private SessionKeyManager sessionKeyManager;

	@RequestMapping(method=RequestMethod.DELETE, value = "/book/{orderId}")
	public String confirm(@PathVariable String orderId, @CookieValue("sessionKey") String sessionKey) {
		sessionKeyManager.isSessionKeyValid(sessionKey);
		Controller controller = context.getBean(Controller.class);
		return controller.confirm(orderId);
	}


	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = (ConfigurableApplicationContext) applicationContext;
	}
}

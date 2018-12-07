package controller.requests;

import controller.Controller;
import controller.SessionKeyManager;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.*;

@RestController
public class MakeOrder implements ApplicationContextAware {

	private ConfigurableApplicationContext context;

	@RequestMapping(method = RequestMethod.POST, value ="/borrow")
	public String borrowBook(@RequestBody String isbn, String libraryID, String customerID, @CookieValue("sessionKey") String sessionKey) {
		SessionKeyManager.checkSessionKey(sessionKey);
		Controller controller = context.getBean(Controller.class);
		return controller.borrowBook(isbn, libraryID, customerID);
	}

	@RequestMapping(method = RequestMethod.POST, value ="/buy")
	public String buyBook(@RequestBody String isbn, String bookstoreID, String customerID, @CookieValue("sessionKey") String sessionKey) {
		SessionKeyManager.checkSessionKey(sessionKey);
		Controller controller = context.getBean(Controller.class);
		return controller.buyBook(isbn, bookstoreID, customerID);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context=(ConfigurableApplicationContext) applicationContext;
	}
}

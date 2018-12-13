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
	public String borrowBook(@RequestBody Order order, @CookieValue("sessionKey") String sessionKey) {
		SessionKeyManager.checkSessionKey(sessionKey);
		Controller controller = context.getBean(Controller.class);
		return controller.borrowBook(order.isbn, order.institutionId, order.customerID);
	}

	@RequestMapping(method = RequestMethod.POST, value ="/buy")
	public String buyBook(@RequestBody Order order, @CookieValue("sessionKey") String sessionKey) {
		SessionKeyManager.checkSessionKey(sessionKey);
		Controller controller = context.getBean(Controller.class);
		return controller.buyBook(order.isbn, order.institutionId, order.customerID);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context=(ConfigurableApplicationContext) applicationContext;
	}

	public static class Order{
		public String isbn;
		public String institutionId;
		public String customerID;

		public Order(){

		}

		public Order(String isbn,String institutionId,String customerID) {
			this.isbn = isbn;
			this.institutionId = institutionId;
			this.customerID = customerID;
		}
	}
}

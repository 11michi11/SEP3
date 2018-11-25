package controller.requests;

import controller.Controller;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MakeOrder implements ApplicationContextAware {

	private ConfigurableApplicationContext context;

	@RequestMapping(method = RequestMethod.POST, value ="/borrow")
	public String borrowBook(@RequestBody String isbn, String libraryID, String customerID) {
		Controller controller = context.getBean(Controller.class);
		return controller.borrowBook(isbn, libraryID, customerID);
	}

	@RequestMapping(method = RequestMethod.POST, value ="/buy")
	public String buyBook(@RequestBody String isbn, String bookstoreID, String customerID) {
		Controller controller = context.getBean(Controller.class);
		return controller.buyBook(isbn, bookstoreID, customerID);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context=(ConfigurableApplicationContext) applicationContext;
	}
}

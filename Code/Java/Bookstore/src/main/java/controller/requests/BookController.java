package controller.requests;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import controller.Controller;
import controller.SessionKeyManager;
import model.Book;

@RestController
public class BookController implements ApplicationContextAware {

	private ConfigurableApplicationContext context;
	@Autowired
	private SessionKeyManager sessionKeyManager;

    @RequestMapping(method=RequestMethod.POST, value="/book")
    public String addBook(@RequestBody Book book, @CookieValue("sessionKey") String sessionKey)
    {
    	System.out.println(book.toString());
        sessionKeyManager.isSessionKeyValid(sessionKey);
        Controller controller = context.getBean(Controller.class);
       
       return controller.addBook(book);
    }

	@RequestMapping(method = RequestMethod.DELETE, value = "/book/{isbn}")
	public String deleteBook(@PathVariable String isbn, @CookieValue("sessionKey") String sessionKey) {
		sessionKeyManager.isSessionKeyValid(sessionKey);
		Controller controller = context.getBean(Controller.class);
		return controller.deleteBook(isbn);

	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = (ConfigurableApplicationContext) applicationContext;
	}


}

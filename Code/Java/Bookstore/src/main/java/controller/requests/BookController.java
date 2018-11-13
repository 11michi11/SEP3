package controller.requests;

import controller.Controller;
import model.Book;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    @RequestMapping(method=RequestMethod.POST, value="/book")
    public String addBook(@RequestBody Book book)
    {
    	System.out.println(book.toString());
        Controller controller = Controller.getInstance();
       
       return controller.addBook(book);
      
    }
    
    @RequestMapping(method=RequestMethod.DELETE, value="/book")
    public String deleteBook(@RequestBody String isbn) {
    	
    	Controller controller = Controller.getInstance();
    	return controller.deleteBook(isbn); 
    	
    }
    
    
}

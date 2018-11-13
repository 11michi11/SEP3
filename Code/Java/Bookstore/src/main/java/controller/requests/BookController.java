package controller.requests;

import controller.Controller;
import model.Book;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {

    @RequestMapping(method=RequestMethod.POST, value="/book")
    public Book addBook(@RequestBody Book book)
    {
    	System.out.println(book.toString());
        Controller controller = Controller.getInstance();
       
       return controller.addBook(book);
      
    }
    
    @RequestMapping(method=RequestMethod.DELETE, value="/book")
    public Book deleteBook(@RequestBody String isbn) {
    	System.out.println("DELETE QW " + isbn + " book pozdro MATEJ");
    	
    	Controller controller = Controller.getInstance();
    	
    	return controller.deleteBook(isbn); 
    	
    }
    
    
}

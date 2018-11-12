package controller.requests;

import controller.Controller;
import model.Book;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CrudController {

    @RequestMapping(method=RequestMethod.POST, value="/book")
    public Book addBook(Book book)
    {
        Controller controller = Controller.getInstance();
        return controller.addBook(book);
    }
}

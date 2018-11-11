package controller.requests;

import controller.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookDetails {

    @CrossOrigin
    @GetMapping("/bookDetails/{isbn}")
    public String getBookDetails(@PathVariable String isbn){
        Controller controller = Controller.getInstance();
        return controller.getBookDetails(isbn);
    }

}

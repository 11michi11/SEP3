package controller.requests;

import controller.Controller;
import controller.connection.DatabaseConnection;
import model.Book;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class Search {

    @RequestMapping("/search")
    public List<Book> search(@RequestParam(value = "searchTerm") String searchTerm) {
        Controller controller = Controller.getInstance();
        return controller.search(searchTerm);
    }
	
	
    @RequestMapping("/searchAdvanced")
    public List<Book> searchAdvanced(@RequestParam(value = "title", defaultValue = "") String title,
                                     @RequestParam(value = "author", defaultValue = "") String author,
                                     @RequestParam(value = "year", required = false) Integer year,
                                     @RequestParam(value = "isbn", defaultValue = "") String isbn,
                                     @RequestParam(value = "category", required = false) Book.Category category) {
        Controller controller = Controller.getInstance();
        if(year == null)
            year = 0;
        if(category == null)
            category = Book.Category.Empty;
        return controller.advancedSearch(title, author, year, isbn, category);
    }
}

package controller.requests;

import controller.Controller;
import controller.SessionKeyManager;
import controller.connection.DatabaseConnection;
import model.Book;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Search{

    private SessionKeyManager sessionKeyManager;
    private Controller controller;

    @Autowired
    public Search(SessionKeyManager sessionKeyManager, Controller controller) {
        this.sessionKeyManager = sessionKeyManager;
        this.controller = controller;
    }

    @RequestMapping("/search")
    public List<Book> search(@RequestParam(value = "searchTerm") String searchTerm,
                             @CookieValue("sessionKey") String sessionKey)
            throws SessionKeyManager.SessionKeyInvalidException {
        sessionKeyManager.isSessionKeyValid(sessionKey);

        return controller.search(searchTerm);
    }

    @RequestMapping("/advancedSearch")
    public List<Book> advancedSearch(@RequestParam(value = "title", defaultValue = "") String title,
                                     @RequestParam(value = "author", defaultValue = "") String author,
                                     @RequestParam(value = "year", required = false) Integer year,
                                     @RequestParam(value = "isbn", defaultValue = "") String isbn,
                                     @RequestParam(value = "category", required = false) Book.Category category,
                                     @CookieValue("sessionKey") String sessionKey) {
        sessionKeyManager.isSessionKeyValid(sessionKey);

        if(year == null)
            year = 0;
        if(category == null)
            category = Book.Category.Empty;
        return controller.advancedSearch(title, author, year, isbn, category);
    }
}

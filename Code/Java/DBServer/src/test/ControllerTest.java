import communication.DBServer;
import communication.Request;
import controller.Controller;
import controller.DBProxy;
import controller.repositories.LibraryRepository;
import controller.repositories.RepositoryManager;
import model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

public class ControllerTest {
//
//    @Mock
//    private DBProxy db;
//    @Mock
//    private DBServer server;
//
//    @InjectMocks
    private Controller controller;

//    @BeforeEach
//    public void setup() {
//       // MockitoAnnotations.initMocks(this);
//        DBProxy db = RepositoryManager.getInstance();
//        DBServer server = new DBServer();
//        controller = new Controller(db, server);
//    }

//    @Test
//    void nullSearchTest(){
//        Map<String, Object> args = new HashMap<>();
//        args.put("isbn", null);
//        args.put("title", "Tolkien");
//        args.put("author", null);
//        args.put("year", null);
//        args.put("category", null);
//        args.put("libraryid", "ce78ef57-77ec-4bb7-82a2-1a78d3789aef");
//        Request request = new Request(Request.Operation.LibraryAdvancedSearch, args);
//
//        String resposne = null;
//        try {
//            resposne = controller.handleLibraryAdvancedSearch(request);
//        } catch (Controller.InvalidOperationException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(resposne);
//    }
//


}

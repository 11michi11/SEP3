package controller.requests;

import controller.SessionKeyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class LogOut {

    @Autowired
    private SessionKeyManager sessionKeyManager;

    @CrossOrigin
    @DeleteMapping("/logOut")
    public String logOut(@CookieValue("sessionKey") String sessionKey) {
        try {
            sessionKeyManager.deleteFromCache(sessionKey);
        } catch (IOException e) {
            e.printStackTrace();
            return "Cannot logged out";
        }
        return "Logged out";
    }
}

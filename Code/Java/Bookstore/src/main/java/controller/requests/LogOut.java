package controller.requests;

import controller.SessionKeyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogOut {

    @Autowired
    private SessionKeyManager sessionKeyManager;

    @CrossOrigin
    @DeleteMapping("/logout")
    public String logOut(@CookieValue("sessionKey") String sessionKey) {
        sessionKeyManager.deleteFromCache(sessionKey);
        return "Logged out";
    }
}

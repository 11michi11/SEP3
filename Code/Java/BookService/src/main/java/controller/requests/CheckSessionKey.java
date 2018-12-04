package controller.requests;

import controller.Controller;
import controller.SessionKeyManager;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;

@RestController
public class CheckSessionKey implements ApplicationContextAware {

	private ConfigurableApplicationContext context;

	@CrossOrigin
	@GetMapping("/checkSK/{sessionKey}")
	public Calendar checkSK(@PathVariable String sessionKey) throws SessionKeyManager.SessionKeyIsNotValidException {
		return SessionKeyManager.checkSK(sessionKey);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

	}
}

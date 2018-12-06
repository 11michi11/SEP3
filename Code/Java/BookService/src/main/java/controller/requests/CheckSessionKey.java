package controller.requests;

import controller.SessionKeyManager;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

@RestController
public class CheckSessionKey implements ApplicationContextAware {

	private ConfigurableApplicationContext context;

	@CrossOrigin
	@GetMapping("/checkSK/{sessionKey}")
	public String checkSK(@PathVariable String sessionKey) {
		Calendar calendar = SessionKeyManager.checkSKFromInstitution(sessionKey);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm:ss");
		sdf.setTimeZone(TimeZone.getDefault());
		System.out.println(calendar.getTime());
		return sdf.format(calendar.getTime());
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context=(ConfigurableApplicationContext) applicationContext;

	}
}

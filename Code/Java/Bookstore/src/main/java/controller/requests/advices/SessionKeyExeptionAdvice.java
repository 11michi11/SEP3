package controller.requests.advices;

import controller.SessionKeyManager;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SessionKeyExeptionAdvice {
	@ResponseBody
	@ExceptionHandler(SessionKeyManager.SessionKeyInvalidException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	String seesionInvalidHandler(SessionKeyManager.SessionKeyInvalidException ex) {
		return ex.getMessage();
	}

}

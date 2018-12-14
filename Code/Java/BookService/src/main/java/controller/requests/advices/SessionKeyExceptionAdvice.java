package controller.requests.advices;

import controller.SessionKeyManager;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SessionKeyExceptionAdvice {

	@ResponseBody
	@ExceptionHandler(SessionKeyManager.SessionKeyIsNotValidException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	String seesionInvalidHandler(SessionKeyManager.SessionKeyIsNotValidException ex) {
		return ex.getMessage();
	}
}

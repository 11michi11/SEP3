package controller.requests.advices;

import controller.connection.DatabaseConnection;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RegisterEmailExceptionAdvice {


    @ResponseBody
    @ExceptionHandler(DatabaseConnection.RegisterEmailException.class)
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    String registerEmailExceptionHandler(DatabaseConnection.RegisterEmailException ex) {
        return ex.getMessage();
    }
}


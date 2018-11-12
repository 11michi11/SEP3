package controller.requests.advices;

import controller.connection.DatabaseConnection;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class DatabaseConnecionExceptionAdvice {


    @ResponseBody
    @ExceptionHandler(DatabaseConnection.SearchException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String wrongSearchHandler(DatabaseConnection.SearchException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(DatabaseConnection.ServerOfflineException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    String serverOfflineHandler(DatabaseConnection.ServerOfflineException ex) {
        return ex.getMessage();
    }


}

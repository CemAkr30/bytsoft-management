package com.codeforinca.bytsoftapi.exceptions;

import com.codeforinca.bytsoftapi.exceptions.base.ApiException;
import com.codeforinca.bytsoftapi.persistence.entites.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.ZonedDateTime;
import java.time.ZoneId;

@RestControllerAdvice(basePackages = {"com.codeforinca.bytsoftapi.controllers", "com.codeforinca.bytsoftapi.auth"})
public class GlobalAdviceException {

    @ExceptionHandler(value = {AuthorizationException.class})
    public ResponseEntity<Object> handleAuthorizationException(AuthorizationException e) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                e,
                HttpStatus.UNAUTHORIZED,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(apiException, HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(value = {ForbidenException.class})
    public ResponseEntity<Object> handleForbidenException(ForbidenException e) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                e,
                HttpStatus.FORBIDDEN,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(apiException, HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(value = {UserException.class})
    public
    ResponseEntity<Object>
            handleUserException(
                    UserException e
    ){
        ApiException apiException = new ApiException(
                e.getMessage(),
                e,
                HttpStatus.FORBIDDEN,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(apiException, HttpStatus.NOT_FOUND);
    }
}

package com.blockchain.blochainapp.security.handler;

import com.blockchain.blochainapp.security.exception.InvalidTokenException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(annotations = SecurityException.class)
public class TokenHandler {

    @ExceptionHandler({InvalidTokenException.class})
    public ResponseEntity<Object> handleInvalidTokenException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

}

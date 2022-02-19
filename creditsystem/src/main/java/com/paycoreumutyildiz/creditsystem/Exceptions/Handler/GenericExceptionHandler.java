package com.paycoreumutyildiz.creditsystem.Exceptions.Handler;

import com.paycoreumutyildiz.creditsystem.Exceptions.CreditQueryException;
import com.paycoreumutyildiz.creditsystem.Exceptions.NotFoundException;
import com.paycoreumutyildiz.creditsystem.Exceptions.UniquePhoneNumberException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GenericExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map> handleNotFoundException(NotFoundException notFoundException){
        Map<String,String> exceptionResponse = new HashMap<>();
        exceptionResponse.put("message",notFoundException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }

    @ExceptionHandler(UniquePhoneNumberException.class)
    public ResponseEntity<Map> handleUniquePhoneNumberException(UniquePhoneNumberException uniquePhoneNumberException){
        Map<String,String> exceptionResponse = new HashMap<>();
        exceptionResponse.put("message",uniquePhoneNumberException.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    @ExceptionHandler(CreditQueryException.class)
    public ResponseEntity<Map> handleCreditQueryException(CreditQueryException creditQueryException){
        Map<String,String> exceptionResponse = new HashMap<>();
        exceptionResponse.put("message",creditQueryException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map> handleException(Exception exception){
        Map<String, String> exceptionResponse = new HashMap<>();
        exceptionResponse.put("message", exception.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(exceptionResponse);
    }
}

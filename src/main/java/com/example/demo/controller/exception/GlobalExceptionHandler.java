package com.example.demo.controller.exception;

import com.example.demo.errors.PersonNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandler {

//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ExceptionHandler(NoSuchBucketException.class)
//    public ServiceErrorResponse exception(NoSuchBucketException exception) {
//        log.warn("NoSuchBucketException: ", exception);
//        return ServiceErrorResponse.builder()
//            .timestamp(LocalDateTime.now().toString())
//            .statusCode(HttpStatus.NOT_FOUND.value())
//            .message("No such bucket")
//            .build();
//    }


    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<String> handlePersonNotFound(PersonNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}

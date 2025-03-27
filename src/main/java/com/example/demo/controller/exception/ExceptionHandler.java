package com.example.demo.controller.exception;

import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {

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
}

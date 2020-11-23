package com.evgenltd.lt.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LogManager.getLogger(ApplicationExceptionHandler.class);

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ResponseEntity<Response<Void>> handle(Throwable throwable) {
        log.error("", throwable);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Response<>("Server error"));
    }
}

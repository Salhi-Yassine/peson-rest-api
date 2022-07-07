package com.example.miniprojet.controller;

import com.example.miniprojet.exception.PersonneNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class PersonneNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(PersonneNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String PersonneNotFoundHandler(PersonneNotFoundException ex) {
        return ex.getMessage();
    }
}
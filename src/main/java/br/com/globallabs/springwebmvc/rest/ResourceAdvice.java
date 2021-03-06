package br.com.globallabs.springwebmvc.rest;

import br.com.globallabs.springwebmvc.excepiton.JediNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ResourceAdvice {

    // sempre vai retornar um status 404 (NOT FOUND) quando
    // existir uma Exception específica, a "JediNotFoundException"
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(JediNotFoundException.class)
    public void notFound () {
    }
}

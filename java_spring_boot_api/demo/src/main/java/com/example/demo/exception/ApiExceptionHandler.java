package com.example.demo.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.persistence.EntityNotFoundException;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler ({EntityNotFoundException.class})
    public String entityNotFound(){
        return "Entity not found";
    }

    @ExceptionHandler ({IllegalArgumentException.class})
    public String iaException(){
        return "Wrong Argument";
    }

    @ExceptionHandler ({NullPointerException.class})
    public String nullException() {
        return "Parameters cannot be null";
    }

//    @ExceptionHandler ({IndexOutOfBoundsException.class})
//    public String indexOutException() {
//        return "Index Out Of Bounds!";
//    }

    @ExceptionHandler ({Exception.class})
    public String generalException() {
        return "Something went wrong";
    }

}


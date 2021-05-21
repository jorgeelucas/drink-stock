package com.challenge.stock.controller.exception;

import com.challenge.stock.service.exception.DrinkTypeInvalidException;
import com.challenge.stock.service.exception.InsufficientCapacityException;
import com.challenge.stock.service.exception.InvalidCategoryException;
import com.challenge.stock.service.exception.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<StandardException> objectNotFound(ObjectNotFoundException e, HttpServletRequest req) {
        StandardException err = new StandardException(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler
    public ResponseEntity<StandardException> insufficientCapacity(InsufficientCapacityException e, HttpServletRequest req) {
        StandardException err = new StandardException(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler
    public ResponseEntity<StandardException> drinkType(DrinkTypeInvalidException e, HttpServletRequest req) {
        StandardException err = new StandardException(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler
    public ResponseEntity<StandardException> invalidCategory(InvalidCategoryException e, HttpServletRequest req) {
        StandardException err = new StandardException(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }
}

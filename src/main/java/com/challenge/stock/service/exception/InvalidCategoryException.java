package com.challenge.stock.service.exception;

public class InvalidCategoryException extends RuntimeException{

    public InvalidCategoryException(String msg) {
        super(msg);
    }

    public InvalidCategoryException(String msg, Throwable cause) {
        super(msg, cause);
    }

}

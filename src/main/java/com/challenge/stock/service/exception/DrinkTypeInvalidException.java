package com.challenge.stock.service.exception;

public class DrinkTypeInvalidException extends RuntimeException {

    public DrinkTypeInvalidException(String msg) {
        super(msg);
    }

    public DrinkTypeInvalidException(String msg, Throwable cause) {
        super(msg, cause);
    }

}

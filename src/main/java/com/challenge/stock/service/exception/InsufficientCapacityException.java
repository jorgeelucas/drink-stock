package com.challenge.stock.service.exception;

public class InsufficientCapacityException extends RuntimeException {

    public InsufficientCapacityException(String msg) {
        super(msg);
    }

    public InsufficientCapacityException(String msg, Throwable cause) {
        super(msg, cause);
    }

}

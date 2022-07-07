package com.example.stocksservice.tinkoff_data.exception;

public class RedisConnectionException extends RuntimeException {

    public RedisConnectionException() {
    }

    public RedisConnectionException(String message) {
        super(message);
    }

    public RedisConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.example.stocksservice.tinkoff_data.exception;

public class CreateReportDirectoryFailedException extends RuntimeException {
    public CreateReportDirectoryFailedException() {
        super();
    }

    public CreateReportDirectoryFailedException(String message) {
        super(message);
    }

    public CreateReportDirectoryFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}

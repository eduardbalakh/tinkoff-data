package com.example.tinkoff_data.exception;

public class CreateReportDirectoryFailed extends RuntimeException {
    public CreateReportDirectoryFailed() {
        super();
    }

    public CreateReportDirectoryFailed(String message) {
        super(message);
    }

    public CreateReportDirectoryFailed(String message, Throwable cause) {
        super(message, cause);
    }
}

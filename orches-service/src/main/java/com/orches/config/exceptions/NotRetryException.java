package com.orches.config.exceptions;

public class NotRetryException extends Exception {
    public NotRetryException(){}

    public NotRetryException(String message) {
        super(message);
    }
}

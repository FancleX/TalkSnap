package com.dev.exception;


public class InvalidAuthException extends RuntimeException {

    public InvalidAuthException(String message) {
        super(message);
    }
}

package com.dev.exception;

public class DuplicatedChannelException extends RuntimeException {

    public DuplicatedChannelException(String message) {
        super(message);
    }
}

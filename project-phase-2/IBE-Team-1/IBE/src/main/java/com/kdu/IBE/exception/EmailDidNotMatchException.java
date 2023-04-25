package com.kdu.IBE.exception;

public class EmailDidNotMatchException extends RuntimeException {
    public EmailDidNotMatchException(String message) {
        super(message);
    }
}

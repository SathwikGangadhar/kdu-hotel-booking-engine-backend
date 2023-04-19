package com.kdu.IBE.exception;
public class RequestTimedException extends RuntimeException{
    public RequestTimedException(String message){
        super(message);
    }
}
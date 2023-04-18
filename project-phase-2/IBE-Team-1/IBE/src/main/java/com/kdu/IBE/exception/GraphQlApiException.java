package com.kdu.IBE.exception;

public class GraphQlApiException extends RuntimeException {
    public GraphQlApiException(String message){
        super(message);
    }
}

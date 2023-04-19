package com.kdu.IBE.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InvalidObjectInput extends RuntimeException{
    public InvalidObjectInput(String message){
        super(message);
    }
}
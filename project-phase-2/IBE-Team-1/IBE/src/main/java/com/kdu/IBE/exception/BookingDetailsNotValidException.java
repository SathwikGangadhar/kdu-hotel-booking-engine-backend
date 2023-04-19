package com.kdu.IBE.exception;

public class BookingDetailsNotValidException extends RuntimeException{
    public BookingDetailsNotValidException(String message){
        super(message);
    }

}

package com.kdu.IBE.exception;

import java.sql.SQLException;

public class UniqueConstraintException extends RuntimeException {
    public UniqueConstraintException(String message){
        super(message);
    }
}

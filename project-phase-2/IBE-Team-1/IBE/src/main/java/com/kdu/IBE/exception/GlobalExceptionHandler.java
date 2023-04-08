package com.kdu.IBE.exception;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;
import java.time.format.DateTimeParseException;
import java.util.zip.DataFormatException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<String> ObjectNotFoundException(ObjectNotFoundException exception){
        log.error(exception.getMessage());
        return new ResponseEntity<>("Oops users not present", HttpStatus.OK);
    }
    @ExceptionHandler(InvalidObjectInput.class)
    public ResponseEntity<String> invalidInput(InvalidObjectInput exception){
        log.error(exception.getMessage());
        return new ResponseEntity<>("Invalid object passed",HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<String> sqlExceptionHandler(SQLException exception){
        log.error(exception.getMessage());
        return new ResponseEntity<>("Error",HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<String> dateTimeParseException(DateTimeParseException exception){
        log.error(exception.getMessage());
        return new ResponseEntity<>("Date given is invalid",HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(DataFormatException.class)
    public ResponseEntity<String> dateFormatException(DataFormatException exception){
        log.error(exception.getMessage());
        return new ResponseEntity<>("Date Format is invalid",HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> nullPointerException(NullPointerException exception){
        log.error(exception.getMessage());
        return new ResponseEntity<>("The parameters in the request passed is invalid",HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> runTimeError(RuntimeException exception){
        log.error(exception.getMessage());
        return new ResponseEntity<>("No Such data present",HttpStatus.BAD_REQUEST);
    }

}

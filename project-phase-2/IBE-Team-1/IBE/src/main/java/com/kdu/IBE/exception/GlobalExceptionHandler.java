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
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> Exception(Exception exception){
        log.error(exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(),HttpStatus.OK);
    }
    @ExceptionHandler(InvalidObjectInput.class)
    public ResponseEntity<String> invalidInput(InvalidObjectInput exception){
        log.error(exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<String> sqlExceptionHandler(SQLException exception){
        log.error(exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<String> dateTimeParseException(DateTimeParseException exception){
        log.error(exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(DataFormatException.class)
    public ResponseEntity<String> dateFormatException(DataFormatException exception){
        log.error(exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> nullPointerException(NullPointerException exception){
        log.error(exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> runTimeError(RuntimeException exception){
        log.error(exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(RoomsNotFoundException.class)
    public ResponseEntity<String> roomsNotFound(RoomsNotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UnexpectedErrorException.class)
    public ResponseEntity<String> unexpectedError(UnexpectedErrorException exception){
        return new ResponseEntity<>(exception.getMessage(),HttpStatus.OK);
    }
    @ExceptionHandler(BookingIdDoesNotExistException.class)
    public ResponseEntity<String> bookingIdDoesNotExistException(BookingIdDoesNotExistException exception){
        return new ResponseEntity<>(exception.getMessage(),HttpStatus.OK);
    }
    @ExceptionHandler(EmailNotSent.class)
    public ResponseEntity<String> emailNotSent(EmailNotSent exception){
        return new ResponseEntity<String >(exception.getMessage(),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(BookingDetailsNotValidException.class)
    public ResponseEntity<String> bookingDetailsNotValid(BookingDetailsNotValidException bookingDetailsNotValidException){
        log.error(bookingDetailsNotValidException.getMessage());
        return new ResponseEntity<>(bookingDetailsNotValidException.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(RequestTimedException.class)
    public ResponseEntity<String> RequestTimedException(RequestTimedException requestTimedException){
        log.error(requestTimedException.getMessage());
        return new ResponseEntity<>(requestTimedException.getMessage(),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(GraphQlApiException.class)
    public ResponseEntity<String> graphQlApiException(GraphQlApiException graphQlApiException){
        log.error(graphQlApiException.getMessage());
        return new ResponseEntity<>(graphQlApiException.getMessage(),HttpStatus.BAD_REQUEST);
    }
}

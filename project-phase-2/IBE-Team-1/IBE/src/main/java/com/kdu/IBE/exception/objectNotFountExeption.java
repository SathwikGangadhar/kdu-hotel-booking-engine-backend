package com.kdu.IBE.exception;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class objectNotFountExeption {
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<String> objectNotfoundException(ObjectNotFoundException exception){
        log.error(exception.getMessage());
        return new ResponseEntity<>("Oops users not present", HttpStatus.OK);
    }
}

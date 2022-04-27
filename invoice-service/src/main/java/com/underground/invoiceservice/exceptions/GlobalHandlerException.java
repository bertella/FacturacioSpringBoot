package com.underground.invoiceservice.exceptions;

import com.underground.Invoiceservice.dto.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalHandlerException {

    @Autowired
    private StandardResponse response;


    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<StandardResponse> resourceNotFoundException(Exception ex){
        response.setStatusCode(HttpStatus.NOT_FOUND.value());
        response.setStatus("ERROR");
        response.setMsg(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<StandardResponse> ilegalArgumentException(Exception ex){
        response.setStatusCode(HttpStatus.BAD_REQUEST.value());
        response.setStatus("ERROR");
        response.setMsg(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ResourceAlreadyExistsException.class})
    public ResponseEntity<StandardResponse> resourceAlreadyExistsException(Exception ex){
        response.setStatusCode(HttpStatus.CONFLICT.value());
        response.setStatus("ERROR");
        response.setMsg(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}

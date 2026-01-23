package com.phone_shop.phoneshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> handleNotFoundException(ApiException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getStatus(), e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidateError(MethodArgumentNotValidException ex) {
        Map<String, String> error = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(err -> error.put(err.getField(), err.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

}

package com.phone_shop.phoneshop.exception;

import org.springframework.http.HttpStatus;


public class ResourceNotFoundException extends ApiException {

    public ResourceNotFoundException(String message, Integer id) {
        super(HttpStatus.NOT_FOUND, "%s with id = %d not found".formatted(message, id));
    }

  
}

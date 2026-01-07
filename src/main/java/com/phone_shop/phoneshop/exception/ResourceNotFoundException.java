package com.phone_shop.phoneshop.exception;

import org.springframework.http.HttpStatus;


public class ResourceNotFoundException extends ApiException {

    public <T> ResourceNotFoundException(String message, T field, T text) {
        super(HttpStatus.NOT_FOUND, message + " with " + text + "= " + field + " not found");
    }


}

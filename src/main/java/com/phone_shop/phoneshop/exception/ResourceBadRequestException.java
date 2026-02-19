package com.phone_shop.phoneshop.exception;

import org.springframework.http.HttpStatus;


public class ResourceBadRequestException extends ApiException {

    public <T> ResourceBadRequestException(String message, T field, T text) {
        super(HttpStatus.BAD_REQUEST, message + " with " + text + " = " + field + " exits");
    }


}

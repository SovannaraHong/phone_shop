package com.phone_shop.phoneshop.exception;

import org.springframework.http.HttpStatus;


public class ResourceBadRequestException extends ApiException {

    public <T> ResourceBadRequestException(String entity, T field, T text, T message) {
        super(HttpStatus.BAD_REQUEST, entity + " with " + field + " = " + text + " " + message);
    }


}

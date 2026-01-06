package com.phone_shop.phoneshop.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
public class ApiException extends RuntimeException {
    private final HttpStatus status;
    private final String message;
}

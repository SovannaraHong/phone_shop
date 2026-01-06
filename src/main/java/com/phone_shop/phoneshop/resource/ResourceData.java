package com.phone_shop.phoneshop.resource;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ResourceData {
    private HttpStatus status;
    private Boolean isResponse;
    private String text;
}

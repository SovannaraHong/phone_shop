package com.phone_shop.phoneshop.util;

import com.phone_shop.phoneshop.payload.response.ApiResponse;
import com.phone_shop.phoneshop.resource.ResourceData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;


public class ResponseUtil {
    public static <T> ApiResponse<T> success(HttpStatus status, String message, T data) {
        return ApiResponse.<T>builder()
                .status(status.value())
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now().toString())
                .build();
    }


    public static ResponseEntity<?> deleteSuccess(String entity, Long id) {
        ResourceData resourceData = new ResourceData(
                HttpStatus.ACCEPTED, true, "%s with id =%d delete Success".formatted(entity, id)

        );
        return ResponseEntity.ok(resourceData);
    }


}

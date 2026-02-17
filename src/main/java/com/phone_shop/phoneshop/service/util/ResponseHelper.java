package com.phone_shop.phoneshop.service.util;

import com.phone_shop.phoneshop.resource.ResourceData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public class ResponseHelper {
    public static ResponseEntity<?> deleteSuccess(String entity, Long id) {
        ResourceData resourceData = new ResourceData(
                HttpStatus.ACCEPTED, true, "%s with id =%d delete Success".formatted(entity, id)

        );
        return ResponseEntity.ok(resourceData);
    }


}

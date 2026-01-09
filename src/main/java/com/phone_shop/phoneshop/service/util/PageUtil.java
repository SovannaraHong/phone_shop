package com.phone_shop.phoneshop.service.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface PageUtil {


    int DEFAULT_PAGE_NUMBER = 1;
    int DEFAULT_PAGE_SIZE = 10;
    String DEFAULT_NUMBER = "_page";
    String DEFAULT_LIMIT = "_limit";

    static Pageable PAGEABLE(int number, int size) {
        if (number < DEFAULT_PAGE_NUMBER) {
            number = DEFAULT_PAGE_NUMBER;
        }
        if (size < 1) {
            size = DEFAULT_PAGE_SIZE;
        }
        return PageRequest.of(number - 1, size);
    }

}

package com.phone_shop.phoneshop.service;

import com.phone_shop.phoneshop.entity.Product;

import java.math.BigDecimal;

public interface ProductService {
    Product create(Product product);

    Product findById(Long id);

    void setSellPrice(Long id, BigDecimal price);
}

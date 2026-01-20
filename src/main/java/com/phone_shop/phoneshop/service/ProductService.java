package com.phone_shop.phoneshop.service;

import com.phone_shop.phoneshop.entity.Product;

public interface ProductService {
    Product create(Product product);

    Product findById(Long id);

}

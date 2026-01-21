package com.phone_shop.phoneshop.controller;


import com.phone_shop.phoneshop.dto.ProductDTO;
import com.phone_shop.phoneshop.entity.Product;
import com.phone_shop.phoneshop.mapper.ProductMapper;
import com.phone_shop.phoneshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/products")
@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ProductDTO dto) {
        Product product = productMapper.toProduct(dto);
        Product products = productService.create(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(products);
    }
}

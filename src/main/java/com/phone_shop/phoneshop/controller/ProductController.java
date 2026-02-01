package com.phone_shop.phoneshop.controller;


import com.phone_shop.phoneshop.dto.ImportProductDTO;
import com.phone_shop.phoneshop.dto.PriceDTO;
import com.phone_shop.phoneshop.dto.ProductDTO;
import com.phone_shop.phoneshop.entity.Product;
import com.phone_shop.phoneshop.mapper.ProductMapper;
import com.phone_shop.phoneshop.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

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

    @PostMapping("{productId}/setPrice")
    public ResponseEntity<?> importPrice(@PathVariable("productId") Long id, @RequestBody PriceDTO priceDTO) {
        productService.setSellPrice(id, priceDTO.getPrice());
        return ResponseEntity.ok("Price updated successfully");


    }

    @PostMapping("uploadProduct")
    public ResponseEntity<?> uploadProduct(@Valid @RequestParam("file") MultipartFile file) {
        Map<Integer, String> errorResponse = productService.uploadProduct(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(errorResponse);

    }

    @PostMapping("importProduct")
    public ResponseEntity<?> importProduct(@Valid @RequestBody ImportProductDTO dto) {
        productService.importProduct(dto);
        return ResponseEntity.ok("Import Product Sucesss");
    }


}

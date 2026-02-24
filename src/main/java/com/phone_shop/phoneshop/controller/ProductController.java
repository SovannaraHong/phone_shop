package com.phone_shop.phoneshop.controller;


import com.phone_shop.phoneshop.dto.ImportProductDTO;
import com.phone_shop.phoneshop.dto.PriceDTO;
import com.phone_shop.phoneshop.dto.ProductDTO;
import com.phone_shop.phoneshop.entity.Product;
import com.phone_shop.phoneshop.mapper.ProductMapper;
import com.phone_shop.phoneshop.service.ProductService;
import com.phone_shop.phoneshop.service.util.ResponseHelper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RequestMapping("/products")
@RequiredArgsConstructor
@RestController

public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProducts());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> findByName(@PathVariable String name) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductByName(name));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(ResponseHelper.deleteSuccess("Product", id));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ProductDTO dto) {
        Product product = productMapper.toProduct(dto);
        Product products = productService.create(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(products);
    }

    @PostMapping("{productId}/setPrice")
    public ResponseEntity<?> importPrice(@PathVariable("productId") Long id, @Valid @RequestBody PriceDTO priceDTO) {
        productService.setSellPrice(id, priceDTO.getPrice());
        return ResponseEntity.ok("Price updated successfully");


    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@Valid @RequestBody ProductDTO dto, @PathVariable Long id) {
        Product product = productService.updateProduct(dto, id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(product);

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

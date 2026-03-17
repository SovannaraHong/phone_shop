package com.phone_shop.phoneshop.controller;


import com.phone_shop.phoneshop.dto.*;
import com.phone_shop.phoneshop.entity.Product;
import com.phone_shop.phoneshop.mapper.ProductMapper;
import com.phone_shop.phoneshop.repository.ProductRepository;
import com.phone_shop.phoneshop.service.ProductService;
import com.phone_shop.phoneshop.service.S3Service;
import com.phone_shop.phoneshop.util.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    private final S3Service s3Service;
    private final ProductRepository productRepository;

//    @GetMapping
//    public ResponseEntity<?> getAllProducts() {
//        return ResponseEntity.status(HttpStatus.OK).body(productService.getProducts());
//    }

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
        return ResponseEntity.ok(ResponseUtil.deleteSuccess("Product", id));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ProductDTO dto) {
        Product product = productMapper.toProduct(dto);
        Product products = productService.create(product, dto.getModelId(), dto.getColorId());
        return ResponseEntity.status(HttpStatus.CREATED).body(productMapper.toResponse(products));
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

    @PutMapping("/{id}/image")
    public ResponseEntity<?> uploadProductImage(
            @PathVariable Long id,
            @RequestPart("file") MultipartFile file) throws Exception {

        Product product = productService.findById(id);

        // Upload image to S3
        String url = s3Service.uploadFile(file, "product_images");

        // Only update the image field
        product.setImagePath(url);

        productRepository.save(product);

        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @GetMapping
    public ResponseEntity<?> getProduct(@RequestParam Map<String, String> params) {

        Page<ProductResponseDTO> products = productService.getProducts(params);
//        PageDTO pageDTO = new PageDTO(products);

        return ResponseEntity.ok(new PageDTO<>(products));
    }

}

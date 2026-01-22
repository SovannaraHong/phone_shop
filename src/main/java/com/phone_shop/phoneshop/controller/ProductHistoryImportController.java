package com.phone_shop.phoneshop.controller;


import com.phone_shop.phoneshop.dto.ImportProductDTO;
import com.phone_shop.phoneshop.entity.ProductHistoryImport;
import com.phone_shop.phoneshop.service.ProductHistoryImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/import")
@RestController

@RequiredArgsConstructor
public class ProductHistoryImportController {
    private final ProductHistoryImportService productHistoryImportService;

    @PostMapping
    public ResponseEntity<?> importProduct(@RequestBody ImportProductDTO dto) {
        ProductHistoryImport productHistoryImport = productHistoryImportService.importProduct(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productHistoryImport);
    }
}

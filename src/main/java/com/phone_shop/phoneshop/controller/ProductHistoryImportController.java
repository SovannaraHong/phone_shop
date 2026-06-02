package com.phone_shop.phoneshop.controller;


import com.phone_shop.phoneshop.dto.ImportProductDTO;
import com.phone_shop.phoneshop.dto.PageDTO;
import com.phone_shop.phoneshop.dto.ProductHistoryImportResponseDTO;
import com.phone_shop.phoneshop.entity.ProductHistoryImport;
import com.phone_shop.phoneshop.service.ProductHistoryImportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/import")
@RestController
@RequiredArgsConstructor
public class ProductHistoryImportController {
    private final ProductHistoryImportService productHistoryImportService;

    //    @PreAuthorize("hasAnyRole('Admin','Manager','Stock')")
//    @PreAuthorize("hasAnyAuthority('productHistory:write')")
    @PostMapping
    public ResponseEntity<?> importProduct(@Valid @RequestBody ImportProductDTO dto) {

        ProductHistoryImport productHistoryImport =
                productHistoryImportService.importProduct(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productHistoryImport);
    }

    //    @PreAuthorize("hasAnyRole('Admin','Manager','Stock')")
//    @PreAuthorize("hasAnyAuthority('productHistory:read')")
    @GetMapping
    public ResponseEntity<PageDTO<ProductHistoryImportResponseDTO>> getProductHistoryImport(
            @RequestParam Map<String, String> params
    ) {

        Page<ProductHistoryImportResponseDTO> productHistory =
                productHistoryImportService.getProductHistory(params);

        return ResponseEntity.ok(new PageDTO<>(productHistory));
    }


}

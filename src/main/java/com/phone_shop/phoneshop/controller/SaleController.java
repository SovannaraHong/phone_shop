package com.phone_shop.phoneshop.controller;

import com.phone_shop.phoneshop.dto.SaleDTO;
import com.phone_shop.phoneshop.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("sales")
@RequiredArgsConstructor
@RestController

public class SaleController {

    private final SaleService saleService;

    @PreAuthorize("hasAnyAuthority('sale:write')")

    @PostMapping
    public ResponseEntity<?> create(@RequestBody SaleDTO saleDTO) {
        saleService.sell(saleDTO);
        return ResponseEntity.ok("Sale Product Success");
    }

    @PreAuthorize("hasAnyAuthority('sale:write')")

    @PutMapping("{saleId}/cancel")
    public ResponseEntity<?> create(@PathVariable Long saleId) {
        saleService.cancelSale(saleId);
        return ResponseEntity.ok("cancel Product Success");
    }


}

package com.phone_shop.phoneshop.controller;

import com.phone_shop.phoneshop.dto.SaleDTO;
import com.phone_shop.phoneshop.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("sales")
@RequiredArgsConstructor
@RestController

public class SaleController {

    private final SaleService saleService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody SaleDTO saleDTO) {
        saleService.sell(saleDTO);
        return ResponseEntity.ok("Sale Product Success");
    }

    @PutMapping("{saleId}/cancel")
    public ResponseEntity<?> create(@PathVariable Long saleId) {
        saleService.cancelSale(saleId);
        return ResponseEntity.ok("cancel Product Success");
    }


}

package com.phone_shop.phoneshop.controller;


import com.phone_shop.phoneshop.dto.BrandDto;
import com.phone_shop.phoneshop.entity.Brand;
import com.phone_shop.phoneshop.repository.BrandRepository;
import com.phone_shop.phoneshop.service.BrandService;
import com.phone_shop.phoneshop.util.BrandMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BrandController {


    @Autowired
    private BrandService brandService;

    @PostMapping("brands")
    public ResponseEntity<?> create(@RequestBody BrandDto brandDto) {
        Brand brands = BrandMapper.toBrand(brandDto);
        Brand brand = brandService.create(brands);
        return ResponseEntity.ok(brand);

    }
}

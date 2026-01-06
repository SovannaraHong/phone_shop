package com.phone_shop.phoneshop.controller;


import com.phone_shop.phoneshop.dto.BrandDto;
import com.phone_shop.phoneshop.entity.Brand;
import com.phone_shop.phoneshop.service.BrandService;
import com.phone_shop.phoneshop.util.BrandMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/brands")
public class BrandController {


    @Autowired
    private BrandService brandService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody BrandDto brandDto) {
        Brand brands = BrandMapper.toBrand(brandDto);
        Brand brand = brandService.create(brands);
        return ResponseEntity.ok(BrandMapper.toBrandDto(brand));
    }


    @GetMapping("{id}")
    public ResponseEntity<?> getOne(@PathVariable(name = "id") Integer brandId) {
        Brand brand = brandService.findById(brandId);
        return ResponseEntity.ok(BrandMapper.toBrandDto(brand));
    }
}

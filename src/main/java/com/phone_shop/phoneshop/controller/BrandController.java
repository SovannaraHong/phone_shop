package com.phone_shop.phoneshop.controller;


import com.phone_shop.phoneshop.dto.BrandDto;
import com.phone_shop.phoneshop.entity.Brand;
import com.phone_shop.phoneshop.mapper.BrandMapper;
import com.phone_shop.phoneshop.service.BrandService;
import com.phone_shop.phoneshop.util.Mapper;
import com.phone_shop.phoneshop.util.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brands")
public class BrandController {


    @Autowired
    private BrandService brandService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody BrandDto brandDto) {
        Brand brands = Mapper.toBrand(brandDto);
//        Brand brands = BrandMapper.INSTANCE.toBrand(brandDto);
        Brand brand = brandService.create(brands);
        return ResponseEntity.status(HttpStatus.CREATED).body(Mapper.toBrandDto(brand));
    }


    @GetMapping("{id}")
    public ResponseEntity<?> getOne(@PathVariable(name = "id") Integer brandId) {
        Brand brand = brandService.findById(brandId);
        return ResponseEntity.ok(Mapper.toBrandDto(brand));
    }

    @GetMapping
    public ResponseEntity<List<Brand>> getAllBrand() {
        List<Brand> allBrand = brandService.getAllBrand();
        return ResponseEntity.ok(allBrand);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody BrandDto brandDto) {
        Brand brand1 = Mapper.toBrand(brandDto);
        Brand brand = brandService.updateBrand(id, brand1);
        return ResponseEntity.ok(Mapper.toBrandDto(brand));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        brandService.delete(id);
        return ResponseEntity.ok(ResponseHelper.deleteSuccess("brand", id));
    }
}

package com.phone_shop.phoneshop.controller;


import com.phone_shop.phoneshop.dto.BrandDto;
import com.phone_shop.phoneshop.entity.Brand;
import com.phone_shop.phoneshop.mapper.BrandMapper;
import com.phone_shop.phoneshop.service.BrandService;
import com.phone_shop.phoneshop.util.ResponseHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/brands")
public class BrandController {


    //    @Autowired
//    private BrandService brandService;


    private final BrandService brandService;
    private final BrandMapper brandMapper;

    public BrandController(BrandService brandService,
                           BrandMapper brandMapper) {
        this.brandService = brandService;
        this.brandMapper = brandMapper;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody BrandDto brandDto) {
        Brand brand = brandMapper.toBrand(brandDto);
        Brand saved = brandService.create(brand);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(brandMapper.toBrandDto(saved));
    }


    @GetMapping("{id}")
    public ResponseEntity<?> getOne(@PathVariable(name = "id") Integer brandId) {
        Brand brand = brandService.findById(brandId);
        return ResponseEntity.ok(brandMapper.toBrandDto(brand));
    }

    @GetMapping
    public ResponseEntity<?> getBrands(
            @RequestParam(required = false) String brandName
    ) {
        if (brandName != null) {
            Brand brand = brandService.getByName(brandName.toLowerCase());
            return ResponseEntity.ok(brandMapper.toBrandDto(brand));
        }

        return ResponseEntity.ok(
                brandService.getAllBrand()
                        .stream()
                        .map(brandMapper::toBrandDto)
                        .toList()
        );
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody BrandDto brandDto) {
        Brand brand1 = brandMapper.toBrand(brandDto);
        Brand brand = brandService.updateBrand(id, brand1);
        return ResponseEntity.ok(brandMapper.toBrandDto(brand));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        brandService.delete(id);
        return ResponseEntity.ok(ResponseHelper.deleteSuccess("brand", id));
    }


}

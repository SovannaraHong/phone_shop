package com.phone_shop.phoneshop.controller;


import com.phone_shop.phoneshop.dto.BrandDto;
import com.phone_shop.phoneshop.dto.ModelDTO;
import com.phone_shop.phoneshop.dto.PageDTO;
import com.phone_shop.phoneshop.entity.Brand;
import com.phone_shop.phoneshop.entity.Model;
import com.phone_shop.phoneshop.mapper.BrandMapper;
import com.phone_shop.phoneshop.mapper.ModelEntityMapper;
import com.phone_shop.phoneshop.service.BrandService;
import com.phone_shop.phoneshop.service.ModelService;
import com.phone_shop.phoneshop.service.util.ResponseHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/brands")
@RequiredArgsConstructor
@RestController
public class BrandController {


    //    @Autowired
//    private BrandService brandService;


    private final BrandService brandService;
    private final BrandMapper brandMapper;
    private final ModelService modelService;
    private final ModelEntityMapper modelEntityMapper;
    

    @PostMapping
    public ResponseEntity<?> create(@RequestBody BrandDto brandDto) {
        Brand brand = brandMapper.toBrand(brandDto);
        Brand saved = brandService.create(brand);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(brandMapper.toBrandDto(saved));
    }


    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable(name = "id") Long brandId) {
        Brand brand = brandService.findById(brandId);
        return ResponseEntity.ok(brandMapper.toBrandDto(brand));

    }

//    @GetMapping
//    public ResponseEntity<?> getBrands(
//            @RequestParam(required = false) String brandName
//    ) {
//        if (brandName != null) {
//            Brand brand = brandService.getByName(brandName.toLowerCase());
//            return ResponseEntity.ok(brandMapper.toBrandDto(brand));
//        }
//
//        return ResponseEntity.ok(
//                brandService.getBrands()
//                        .stream()
//                        .map(brandMapper::toBrandDto)
//                        .toList()
//        );
//    }


    @GetMapping("{id}/models")
    public ResponseEntity<?> getModelByBrandId(@PathVariable(name = "id") Long brandId) {
        List<Model> modelByBrandId = modelService.getModelByBrandId(brandId);
        List<ModelDTO> list = modelByBrandId.stream().map(modelEntityMapper::toModelDTO).toList();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(list);
    }

    @GetMapping
    public ResponseEntity<?> getBrands(@RequestParam Map<String, String> params) {
        Page<Brand> brands = brandService.getBrands(params);
        PageDTO pageDTO = new PageDTO(brands);

//        List<BrandDto> list = brandService.getBrands(params)
//                .stream()
//                .map(brandMapper::toBrandDto)
//                .toList();
        return ResponseEntity.ok(pageDTO);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody BrandDto brandDto) {
        Brand brand1 = brandMapper.toBrand(brandDto);
        Brand brand = brandService.updateBrand(id, brand1);
        return ResponseEntity.ok(brandMapper.toBrandDto(brand));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        brandService.delete(id);
        return ResponseEntity.ok(ResponseHelper.deleteSuccess("brand", id));
    }


}

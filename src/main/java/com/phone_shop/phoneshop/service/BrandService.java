package com.phone_shop.phoneshop.service;


import com.phone_shop.phoneshop.entity.Brand;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;


public interface BrandService {
    Brand create(Brand brand);

    Brand findById(Long id); //TODO CREATE

    void delete(Long id);

    Brand updateBrand(Long id, Brand brand); // TODO UPDATE

    List<Brand> getBrands(); //TODO GET ALL

    Page<Brand> getBrands(Map<String, String> params);

    Brand getByName(String name);


}

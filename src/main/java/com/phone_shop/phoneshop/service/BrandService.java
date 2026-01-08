package com.phone_shop.phoneshop.service;


import com.phone_shop.phoneshop.entity.Brand;

import java.util.List;
import java.util.Map;


public interface BrandService {
    Brand create(Brand brand);

    Brand findById(Integer id); //TODO CREATE

    void delete(Integer id);

    Brand updateBrand(Integer id, Brand brand); // TODO UPDATE

    List<Brand> getBrands(); //TODO GET ALL

    List<Brand> getBrands(Map<String, String> params);

    Brand getByName(String name);


}

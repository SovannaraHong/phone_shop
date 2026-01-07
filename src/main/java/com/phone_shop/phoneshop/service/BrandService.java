package com.phone_shop.phoneshop.service;


import com.phone_shop.phoneshop.entity.Brand;

import java.util.List;


public interface BrandService {
    Brand create(Brand brand);

    Brand findById(Integer id); //TODO CREATE

    List<Brand> getAllBrand(); //TODO GET ALL

    Brand updateBrand(Integer id, Brand brand); // TODO UPDATE

    Brand getByName(String name);

    void delete(Integer id);

}

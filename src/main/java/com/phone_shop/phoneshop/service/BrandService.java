package com.phone_shop.phoneshop.service;


import com.phone_shop.phoneshop.entity.Brand;
import org.springframework.stereotype.Service;

import java.util.List;


public interface BrandService {
    Brand create(Brand brand);

    Brand getOne(Integer id); //TODO CREATE

    List<Brand> getAllBrand(); //TODO GET ALL

    Brand updateBrand(Integer id, Brand brand); // TODO UPDATE

}

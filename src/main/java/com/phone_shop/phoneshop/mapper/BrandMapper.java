package com.phone_shop.phoneshop.mapper;


import com.phone_shop.phoneshop.dto.BrandDto;
import com.phone_shop.phoneshop.entity.Brand;


public interface BrandMapper {
    Brand toBrand(BrandDto brandDto);
}

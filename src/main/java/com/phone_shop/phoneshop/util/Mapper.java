package com.phone_shop.phoneshop.util;

import com.phone_shop.phoneshop.dto.BrandDto;
import com.phone_shop.phoneshop.entity.Brand;

public class Mapper {
    public static Brand toBrand(BrandDto brandDto) {
        Brand brand = new Brand();
        brand.setName(brandDto.getName());
        brand.setCountry(brandDto.getCountry());
        return brand;
    }

    public static BrandDto toBrandDto(Brand brand) {
        BrandDto brandDto = new BrandDto();
        brandDto.setName(brand.getName());
        brandDto.setCountry(brand.getCountry());
        return brandDto;
    }
}

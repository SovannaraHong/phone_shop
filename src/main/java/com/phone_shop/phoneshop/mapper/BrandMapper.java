package com.phone_shop.phoneshop.mapper;


import com.phone_shop.phoneshop.dto.BrandDto;
import com.phone_shop.phoneshop.entity.Brand;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")

public interface BrandMapper {

    Brand toBrand(BrandDto brandDto);

    BrandDto toBrandDto(Brand brand);

}

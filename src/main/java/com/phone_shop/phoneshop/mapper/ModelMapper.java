package com.phone_shop.phoneshop.mapper;


import com.phone_shop.phoneshop.dto.ModelDTO;
import com.phone_shop.phoneshop.entity.Model;
import com.phone_shop.phoneshop.service.BrandService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {BrandService.class})
public interface ModelMapper {

    @Mapping(target = "brand", source = "brandId")
    Model toModel(ModelDTO dto);
}

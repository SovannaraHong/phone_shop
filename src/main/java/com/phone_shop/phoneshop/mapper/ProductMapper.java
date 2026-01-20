package com.phone_shop.phoneshop.mapper;


import com.phone_shop.phoneshop.dto.ProductDTO;
import com.phone_shop.phoneshop.entity.Product;
import com.phone_shop.phoneshop.service.ColorService;
import com.phone_shop.phoneshop.service.ModelService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ModelService.class, ColorService.class})
public interface ProductMapper {
    @Mapping(target = "model", source = "modelId")
    @Mapping(target = "color", source = "colorId")
    Product toProduct(ProductDTO dto);

    @Mapping(target = "modelId", source = "model.id")
    @Mapping(target = "colorId", source = "color.id")
    ProductDTO toProductDto(Product product);
}

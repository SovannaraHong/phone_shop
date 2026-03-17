package com.phone_shop.phoneshop.mapper;


import com.phone_shop.phoneshop.dto.ImportProductDTO;
import com.phone_shop.phoneshop.dto.ProductDTO;
import com.phone_shop.phoneshop.dto.ProductResponseDTO;
import com.phone_shop.phoneshop.entity.Product;
import com.phone_shop.phoneshop.entity.ProductHistoryImport;
import com.phone_shop.phoneshop.service.ColorService;
import com.phone_shop.phoneshop.service.ModelService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ModelService.class, ColorService.class})
public interface ProductMapper {
    //    @Mapping(target = "model", source = "modelId")
//    @Mapping(target = "color", source = "colorId")
    @Mapping(target = "model", ignore = true)
    @Mapping(target = "color", ignore = true)
    Product toProduct(ProductDTO dto);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product", ignore = true)
    ProductHistoryImport toProduct(ImportProductDTO dto);

    @Mapping(target = "modelName", source = "model.name")
    @Mapping(target = "colorName", source = "color.name")
    @Mapping(target = "brandId", source = "model.brand.id")
    ProductResponseDTO toResponse(Product product);
}

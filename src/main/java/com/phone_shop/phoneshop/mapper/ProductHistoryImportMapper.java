package com.phone_shop.phoneshop.mapper;


import com.phone_shop.phoneshop.dto.ImportProductDTO;
import com.phone_shop.phoneshop.entity.ProductHistoryImport;
import com.phone_shop.phoneshop.service.ProductService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProductService.class})
public interface ProductHistoryImportMapper {
    @Mapping(target = "product", source = "productId")
    ProductHistoryImport toProduct(ImportProductDTO dto);
}

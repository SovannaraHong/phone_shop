package com.phone_shop.phoneshop.mapper;


import com.phone_shop.phoneshop.dto.ImportProductDTO;
import com.phone_shop.phoneshop.entity.ProductHistoryImport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductHistoryImportMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product", ignore = true)
    ProductHistoryImport toProduct(ImportProductDTO dto);
}

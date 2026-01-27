package com.phone_shop.phoneshop.mapper;


import com.phone_shop.phoneshop.dto.ProductSoldDTO;
import com.phone_shop.phoneshop.dto.SaleDTO;
import com.phone_shop.phoneshop.entity.Sale;
import com.phone_shop.phoneshop.entity.SaleDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SaleMapper {
    Sale toSale(SaleDTO saleDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sale", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "amount", ignore = true)
    @Mapping(target = "unitSale", ignore = true)
    SaleDetail toSaleDetail(ProductSoldDTO productSoldDTO);
}


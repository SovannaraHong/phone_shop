package com.phone_shop.phoneshop.mapper;


import com.phone_shop.phoneshop.dto.ModelDTO;
import com.phone_shop.phoneshop.entity.Model;
import com.phone_shop.phoneshop.service.BrandService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {BrandService.class})
public interface ModelEntityMapper {


    //convert Integer to object brand
    @Mapping(target = "brand", source = "brandId")
    Model toModel(ModelDTO dto);

    @Mapping(target = "brandId", source = "brand.id")
    ModelDTO toModelDTO(Model model);


    // Custom method to convert Integer to Brand
//    default Brand map(Integer brandId) {
//        if (brandId == null) {
//            return null;
//        }
//        Brand brand = new Brand();
//        brand.setId(brandId);
//        return brand;
//    }
}

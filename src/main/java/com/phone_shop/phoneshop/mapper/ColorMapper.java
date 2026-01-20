package com.phone_shop.phoneshop.mapper;

import com.phone_shop.phoneshop.dto.ColorDTO;
import com.phone_shop.phoneshop.entity.Color;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ColorMapper {
    Color toColor(ColorDTO dto);

    ColorDTO toColorDTO(Color color);
}


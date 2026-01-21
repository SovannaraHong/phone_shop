package com.phone_shop.phoneshop.service;


import com.phone_shop.phoneshop.dto.ColorDTO;
import com.phone_shop.phoneshop.entity.Color;

public interface ColorService {
    Color create(ColorDTO dto);

    Color findById(Long id);

    Color update(Long id, Color color);

    void delete(Long id);


}

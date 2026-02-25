package com.phone_shop.phoneshop.service;


import com.phone_shop.phoneshop.dto.ColorDTO;
import com.phone_shop.phoneshop.entity.Color;

import java.util.List;

public interface ColorService {
    //IMPROVE CREATE
    Color create(ColorDTO dto);

    Color findById(Long id);

    List<Color> getColors();

    Color findByName(String name);

    // IMPROVE UPDATE
    Color update(Long id, Color color);

    void delete(Long id);


}

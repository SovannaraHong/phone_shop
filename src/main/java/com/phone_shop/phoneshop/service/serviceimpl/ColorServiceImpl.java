package com.phone_shop.phoneshop.service.serviceimpl;

import com.phone_shop.phoneshop.dto.ColorDTO;
import com.phone_shop.phoneshop.entity.Color;
import com.phone_shop.phoneshop.exception.ResourceNotFoundException;
import com.phone_shop.phoneshop.mapper.ColorMapper;
import com.phone_shop.phoneshop.repository.ColorRepository;
import com.phone_shop.phoneshop.service.ColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ColorServiceImpl implements ColorService {
    private final ColorRepository colorRepository;
    private final ColorMapper colorMapper;

    @Override
    public Color create(ColorDTO dto) {
        Color toColor = colorMapper.toColor(dto);
        return colorRepository.save(toColor);

    }

    @Override
    public Color findById(Long id) {
        Optional<Color> color = colorRepository.findById(id);
        return color
                .orElseThrow(() -> new ResourceNotFoundException("color", id, "id"));

    }

    @Override
    public Color update(Long id, Color color) {
        Color colorId = findById(id);
        colorId.setName(color.getName());
        return colorRepository.save(colorId);

    }

    @Override
    public void delete(Long id) {
        Color colorId = findById(id);
        colorRepository.delete(colorId);
    }

}

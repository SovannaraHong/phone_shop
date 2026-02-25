package com.phone_shop.phoneshop.service.serviceimpl;

import com.phone_shop.phoneshop.dto.ColorDTO;
import com.phone_shop.phoneshop.entity.Color;
import com.phone_shop.phoneshop.exception.ResourceBadRequestException;
import com.phone_shop.phoneshop.exception.ResourceNotFoundException;
import com.phone_shop.phoneshop.mapper.ColorMapper;
import com.phone_shop.phoneshop.repository.ColorRepository;
import com.phone_shop.phoneshop.service.ColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ColorServiceImpl implements ColorService {
    private final ColorRepository colorRepository;
    private final ColorMapper colorMapper;

    @Override
    public Color create(ColorDTO dto) {
        Color toColor = colorMapper.toColor(dto);
        if (colorRepository.existsByName(toColor.getName())) {
            throw new ResourceBadRequestException("Color", "name", toColor.getName(), "Color already exists");
        }
        return colorRepository.save(toColor);

    }

    @Override
    public Color findById(Long id) {
        Optional<Color> color = colorRepository.findById(id);
        return color
                .orElseThrow(() -> new ResourceNotFoundException("color", id, "id"));

    }

    @Override
    public List<Color> getColors() {
        return colorRepository.findAll();
    }

    @Override
    public Color findByName(String name) {
        return colorRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("color", name, "name"));
    }

    @Override
    public Color update(Long id, Color color) {

        Color colorId = findById(id);
        if (colorRepository.existsByName(colorId.getName())) {
            throw new ResourceBadRequestException("Color", "name", color.getName(), "Color already exists");
        }
        colorId.setName(color.getName());
        return colorRepository.save(colorId);

    }

    @Override
    public void delete(Long id) {
        Color colorId = findById(id);
        colorRepository.delete(colorId);
    }

}

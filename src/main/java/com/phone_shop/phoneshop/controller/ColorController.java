package com.phone_shop.phoneshop.controller;

import com.phone_shop.phoneshop.dto.ColorDTO;
import com.phone_shop.phoneshop.entity.Color;
import com.phone_shop.phoneshop.mapper.ColorMapper;
import com.phone_shop.phoneshop.service.ColorService;
import com.phone_shop.phoneshop.service.util.ResponseHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/colors")
@RequiredArgsConstructor
public class ColorController {

    private final ColorService colorService;
    private final ColorMapper colorMapper;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ColorDTO dto) {
        Color color = colorService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(color);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Color color = colorService.findById(id);
        ColorDTO colorDTO = colorMapper.toColorDTO(color);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(colorDTO);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ColorDTO dto) {
        Color color = colorMapper.toColor(dto);
        Color updateColor = colorService.update(id, color);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(colorMapper.toColorDTO(updateColor));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        colorService.delete(id);
        return ResponseEntity.ok(ResponseHelper.deleteSuccess("color", id));
    }

}

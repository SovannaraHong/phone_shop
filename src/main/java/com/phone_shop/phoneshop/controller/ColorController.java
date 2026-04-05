package com.phone_shop.phoneshop.controller;

import com.phone_shop.phoneshop.dto.ColorDTO;
import com.phone_shop.phoneshop.entity.Color;
import com.phone_shop.phoneshop.mapper.ColorMapper;
import com.phone_shop.phoneshop.service.ColorService;
import com.phone_shop.phoneshop.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/colors")
@RequiredArgsConstructor
@RestController

public class ColorController {

    private final ColorService colorService;
    private final ColorMapper colorMapper;

    @PreAuthorize("hasAnyAuthority('color:write')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody ColorDTO dto) {
        Color color = colorService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(color);
    }

    @PreAuthorize("hasAnyAuthority('color:read')")

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Color color = colorService.findById(id);
        ColorDTO colorDTO = colorMapper.toColorDTO(color);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(colorDTO);
    }

    @PreAuthorize("hasAnyAuthority('color:read')")

    @GetMapping
    public ResponseEntity<?> getAllColors() {
        return ResponseEntity.ok().body(colorService.getColors());
    }

    @PreAuthorize("hasAnyAuthority('color:read')")

    @GetMapping("name/{name}")
    public ResponseEntity<?> findByName(@PathVariable String name) {
        return ResponseEntity.ok().body(colorService.findByName(name));
    }

    @PreAuthorize("hasAnyAuthority('color:write')")

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ColorDTO dto) {
        Color color = colorMapper.toColor(dto);
        Color updateColor = colorService.update(id, color);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(colorMapper.toColorDTO(updateColor));
    }

    @PreAuthorize("hasAnyAuthority('color:write')")

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        colorService.delete(id);
        return ResponseEntity.ok(ResponseUtil.deleteSuccess("color", id));
    }

}

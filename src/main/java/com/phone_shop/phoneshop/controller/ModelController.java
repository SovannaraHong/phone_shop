package com.phone_shop.phoneshop.controller;


import com.phone_shop.phoneshop.dto.ModelDTO;
import com.phone_shop.phoneshop.entity.Model;
import com.phone_shop.phoneshop.mapper.ModelEntityMapper;
import com.phone_shop.phoneshop.service.ModelService;
import com.phone_shop.phoneshop.service.util.ResponseHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/models")
@RequiredArgsConstructor

public class ModelController {

    private final ModelService modelService;
    private final ModelEntityMapper modelMapper;

    //TODO IMPROVE FUNCTION
    @PostMapping
    public ResponseEntity<?> create(@RequestBody ModelDTO modelDTO) {
        Model model = modelMapper.toModel(modelDTO);
        model = modelService.create(model);
        return ResponseEntity.status(HttpStatus.CREATED).body(model);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getModelById(@PathVariable Long id) {
        Model modelId = modelService.getModelId(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(modelMapper.toModelDTO(modelId));
    }

    @PutMapping
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ModelDTO modelDTO) {
        Model model = modelMapper.toModel(modelDTO);
        Model modelUpdate = modelService.update(id, model);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(modelUpdate);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        modelService.delete(id);
        return ResponseHelper.deleteSuccess("model", id);

    }


}

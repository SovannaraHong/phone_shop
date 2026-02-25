package com.phone_shop.phoneshop.service.serviceimpl;

import com.phone_shop.phoneshop.entity.Model;
import com.phone_shop.phoneshop.exception.ResourceBadRequestException;
import com.phone_shop.phoneshop.exception.ResourceNotFoundException;
import com.phone_shop.phoneshop.repository.ModelRepository;
import com.phone_shop.phoneshop.service.ModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ModelServiceImpl implements ModelService {

    private final ModelRepository modelRepository;


    @Override
    public Model create(Model model) {
        if (modelRepository.existsByName(model.getName())) {
            throw new ResourceBadRequestException("Model", "name", model.getName(), "Model already exits");
        }
        return modelRepository.save(model);
    }

    @Override
    public List<Model> getModelByBrandId(Long id) {
        return modelRepository.findByBrandId(id);
    }

    @Override
    public List<Model> getModels() {
        return modelRepository.findAll();
    }

    @Override
    public Model getModelByName(String name) {
        return modelRepository.findByName(name);
    }

    @Override
    public Model getModelId(Long id) {
        return modelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("model", id, "id"));
    }

    @Override
    public Model update(Long id, Model model) {
        Model modelId = getModelId(id);
        if (modelRepository.existsByName(model.getName())) {
            throw new ResourceBadRequestException("Model", "name", model.getName(), "Model already exits");
        }
        modelId.setName(model.getName());
        return modelRepository.save(modelId);
    }

    @Override
    public void delete(Long id) {
        Model modelId = getModelId(id);
        modelRepository.delete(modelId);
    }


}

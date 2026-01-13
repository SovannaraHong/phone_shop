package com.phone_shop.phoneshop.service.serviceimpl;

import com.phone_shop.phoneshop.entity.Model;
import com.phone_shop.phoneshop.repository.ModelRepository;
import com.phone_shop.phoneshop.service.ModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ModelServiceImpl implements ModelService {

    private final ModelRepository modelRepository;


    @Override
    public Model create(Model model) {
        return modelRepository.save(model);
    }
}

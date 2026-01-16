package com.phone_shop.phoneshop.service;

import com.phone_shop.phoneshop.entity.Model;

import java.util.List;

public interface ModelService {
    Model create(Model model);

    List<Model> getModelByBrandId(Long id);

    Model getModelId(Long id);


}

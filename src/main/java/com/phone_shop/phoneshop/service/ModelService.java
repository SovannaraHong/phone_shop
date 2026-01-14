package com.phone_shop.phoneshop.service;

import com.phone_shop.phoneshop.entity.Model;

import java.util.List;

public interface ModelService {
    Model create(Model model);

    List<Model> getModelByBrandId(Integer id);

    Model getModelId(Integer id);


}

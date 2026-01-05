package com.phone_shop.phoneshop.serviceimpl;

import com.phone_shop.phoneshop.entity.Brand;
import com.phone_shop.phoneshop.repository.BrandRepository;
import com.phone_shop.phoneshop.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandRepository brandRepository;


    @Override
    public Brand create(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public Brand getOne(Integer id) {
        return null;
    }

    @Override
    public List<Brand> getAllBrand() {
        return List.of();
    }

    @Override
    public Brand updateBrand(Integer id, Brand brand) {
        return null;
    }
}

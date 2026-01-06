package com.phone_shop.phoneshop.serviceimpl;

import com.phone_shop.phoneshop.entity.Brand;
import com.phone_shop.phoneshop.exception.ApiException;
import com.phone_shop.phoneshop.exception.ResourceNotFoundException;
import com.phone_shop.phoneshop.repository.BrandRepository;
import com.phone_shop.phoneshop.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandRepository brandRepository;


    @Override
    public Brand create(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public Brand findById(Integer id) {
        Optional<Brand> brandId = brandRepository.findById(id);
        return brandId.orElseThrow(() -> new ResourceNotFoundException("brand", id));
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

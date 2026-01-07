package com.phone_shop.phoneshop.serviceimpl;

import com.phone_shop.phoneshop.entity.Brand;
import com.phone_shop.phoneshop.exception.ResourceNotFoundException;
import com.phone_shop.phoneshop.repository.BrandRepository;
import com.phone_shop.phoneshop.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
        return brandId.orElseThrow(() -> new ResourceNotFoundException("brand", id, "id"));
    }

    @Override
    public List<Brand> getAllBrand() {
        return brandRepository.findAll(
                Sort.by(Sort.Direction.ASC, "id")
        );
    }

    @Override
    public Brand updateBrand(Integer id, Brand brand) {
        Brand byId = findById(id);
        byId.setName(brand.getName());
        byId.setCountry(brand.getCountry());
        return brandRepository.save(byId);


    }

    @Override
    public Brand getByName(String name) {
        Optional<Brand> byNameIgnoreCase = brandRepository.findByNameContaining(name);
        return byNameIgnoreCase.
                orElseThrow(() -> new ResourceNotFoundException("Brand", name, "name"));

    }


    @Override
    public void delete(Integer id) {
        Brand brandDel = findById(id);
        brandRepository.delete(brandDel);
    }

}

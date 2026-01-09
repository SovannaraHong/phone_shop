package com.phone_shop.phoneshop.service.serviceimpl;

import com.phone_shop.phoneshop.entity.Brand;
import com.phone_shop.phoneshop.exception.ResourceNotFoundException;
import com.phone_shop.phoneshop.repository.BrandRepository;
import com.phone_shop.phoneshop.service.BrandService;
import com.phone_shop.phoneshop.service.util.PageUtil;
import com.phone_shop.phoneshop.specification.BrandFilter;
import com.phone_shop.phoneshop.specification.BrandSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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
    public List<Brand> getBrands() {
        return brandRepository.findAll(
                Sort.by(Sort.Direction.ASC, "id")
        );
    }

    @Override
    public Page<Brand> getBrands(Map<String, String> params) {

        BrandFilter brandFilter = new BrandFilter();
        if (params.containsKey("name")) {
            String name = params.get("name");
            brandFilter.setName(name);
        }
        if (params.containsKey("id")) {
            String id = params.get("id");
            brandFilter.setId(Integer.parseInt(id));
        }
        if (params.containsKey("country")) {
            String country = params.get("country");
            brandFilter.setCountry(country);
        }
        int pageNumber = PageUtil.DEFAULT_PAGE_NUMBER;
        int pageLimit = PageUtil.DEFAULT_PAGE_SIZE;
        if (params.containsKey(PageUtil.DEFAULT_NUMBER)) {
            pageNumber = Integer.parseInt(params.get(PageUtil.DEFAULT_NUMBER));
        }
        if (params.containsKey(PageUtil.DEFAULT_LIMIT)) {
            pageLimit = Integer.parseInt(params.get(PageUtil.DEFAULT_LIMIT));
        }
        BrandSpec brandSpec = new BrandSpec(brandFilter);
        Pageable pageable = PageUtil.PAGEABLE(pageNumber, pageLimit);

        return brandRepository.findAll(brandSpec, pageable);

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

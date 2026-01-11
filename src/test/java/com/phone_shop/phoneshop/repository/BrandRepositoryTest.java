package com.phone_shop.phoneshop.repository;

import com.phone_shop.phoneshop.entity.Brand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;


@DataJpaTest
class BrandRepositoryTest {


    @Autowired
    private BrandRepository brandRepository;

    @Test
    void findByNameContainingTest() {
        //given
        Brand brand = new Brand();
        brand.setName("apple");
        brand.setCountry("cambodia");
        //when
        brandRepository.save(brand);
        Optional<Brand> result = brandRepository.findByNameContaining("a");
//then
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("apple", result.get().getName());
        Assertions.assertEquals("cambodia", result.get().getCountry());

    }

}
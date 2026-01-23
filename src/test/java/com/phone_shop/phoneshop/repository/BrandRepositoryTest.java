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
        // 1. Given: Save a real entity to the in-memory DB
        Brand brand = new Brand();
        brand.setName("apple");
        brand.setCountry("cambodia");
        Brand savedBrand = brandRepository.save(brand); // This generates the ID

        // 2. When: Call the real method (No Mockito.when() here!)
        Optional<Brand> foundBrand = brandRepository.findById(savedBrand.getId());

        // 3. Then: Assert the results
        Assertions.assertTrue(foundBrand.isPresent());
        Assertions.assertEquals("apple", foundBrand.get().getName());
        Assertions.assertEquals("cambodia", foundBrand.get().getCountry());
    }
}

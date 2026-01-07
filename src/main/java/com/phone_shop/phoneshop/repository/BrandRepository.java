package com.phone_shop.phoneshop.repository;

import com.phone_shop.phoneshop.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Integer> {

    Optional<Brand> findByNameContaining(String name);
}

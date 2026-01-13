package com.phone_shop.phoneshop.repository;

import com.phone_shop.phoneshop.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelRepository extends JpaRepository<Model, Integer> {
}

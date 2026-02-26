package com.phone_shop.phoneshop.repository;

import com.phone_shop.phoneshop.entity.Color;
import com.phone_shop.phoneshop.entity.Model;
import com.phone_shop.phoneshop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @Query("""
            SELECT p FROM Product p
            WHERE p.model.id = :modelId AND p.color.id = :colorId
            """)
    Optional<Product> findModelIdAndColorId(Long modelId, Long colorId);

    Optional<Product> findProductByName(String name);

    boolean existsByModelAndColor(Model model, Color color);

}

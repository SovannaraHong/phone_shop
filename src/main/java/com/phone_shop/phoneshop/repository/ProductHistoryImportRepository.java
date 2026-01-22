package com.phone_shop.phoneshop.repository;

import com.phone_shop.phoneshop.entity.ProductHistoryImport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductHistoryImportRepository extends JpaRepository<ProductHistoryImport, Long> {
}

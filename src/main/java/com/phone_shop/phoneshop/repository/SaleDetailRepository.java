package com.phone_shop.phoneshop.repository;

import com.phone_shop.phoneshop.entity.SaleDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SaleDetailRepository extends JpaRepository<SaleDetail, Long> {
}

package com.phone_shop.phoneshop.specification;

import com.phone_shop.phoneshop.entity.Sale;
import com.phone_shop.phoneshop.entity.SaleDetail;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
public class SaleDetailSpec implements Specification<SaleDetail> {

    private SaleDetailFilter saleDetailFilter;

    @Override
    public Predicate toPredicate(Root<SaleDetail> saleDetail, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        Join<SaleDetail, Sale> sale = saleDetail.join("sale");
        if (Objects.nonNull(saleDetailFilter.getStartDate())) {
            cb.greaterThanOrEqualTo(sale.get("soldDate"), saleDetailFilter.getStartDate());
        }
        if (Objects.nonNull(saleDetailFilter.getEndDate())) {
            cb.greaterThanOrEqualTo(sale.get("soldDate"), saleDetailFilter.getEndDate());
        }

        return cb.and(predicates.toArray(Predicate[]::new));
    }
}

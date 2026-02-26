package com.phone_shop.phoneshop.specification;

import com.phone_shop.phoneshop.entity.Product;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public record ProductSpec(ProductFilter productFilter) implements Specification<Product> {
    @Override
    public Predicate toPredicate(Root product, CriteriaQuery query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(productFilter.getId())) {
            predicates.add(cb.equal(product.get("id"), productFilter.getId()));
        }
        if (Objects.nonNull(productFilter.getName())) {
            predicates.add(cb.like(product.get("name"), "%" + productFilter.getName() + "%"));
        }

        return cb.and(predicates.toArray(Predicate[]::new));
    }
}

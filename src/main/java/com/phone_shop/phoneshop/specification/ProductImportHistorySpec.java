package com.phone_shop.phoneshop.specification;

import com.phone_shop.phoneshop.entity.ProductHistoryImport;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
public class ProductImportHistorySpec implements Specification<ProductHistoryImport> {
    private ProductImportHistoryFilter productImportHistoryFilter;

    @Override
    public Predicate toPredicate(Root<ProductHistoryImport> productHistoryImport, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(productImportHistoryFilter.getStartDate())) {
            LocalDateTime localDateTime = productImportHistoryFilter.getStartDate().atStartOfDay();
            predicates.add(cb.greaterThanOrEqualTo(productHistoryImport.get("importDate"), localDateTime));

        }
        if (Objects.nonNull(productImportHistoryFilter.getEndDate())) {
            LocalDateTime localDateTime = productImportHistoryFilter.getEndDate().atTime(LocalTime.MAX);
            predicates.add(cb.lessThanOrEqualTo(productHistoryImport.get("importDate"), localDateTime));
        }
        return cb.and(predicates.toArray(Predicate[]::new));
    }
}

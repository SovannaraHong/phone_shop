package com.phone_shop.phoneshop.specification;

import com.phone_shop.phoneshop.entity.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Objects;

@Data
@AllArgsConstructor
public class UserSpec implements Specification<User> {
    private final UserFilter userFilter;

    @Override
    public Predicate toPredicate(Root<User> user, CriteriaQuery<?> query, CriteriaBuilder cb) {

        ArrayList<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(userFilter.getId())) {
            predicates.add(cb.equal(user.get("id"), userFilter.getId()));
        }
        if (Objects.nonNull(userFilter.getUsername())) {
            predicates.add(cb.equal(user.get("username"), userFilter.getUsername()));
        }
        return cb.and(predicates.toArray(Predicate[]::new));
    }
}

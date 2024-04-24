package org.example.users.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.example.users.dto.UserForFilterDto;
import org.example.users.entity.User;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserSpecification implements Specification<User> {
    private UserForFilterDto userForFilterDto;
    public UserSpecification(UserForFilterDto userForFilterDto) {
        this.userForFilterDto = userForFilterDto;
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if(userForFilterDto.getFromDate()!=null)criteriaBuilder.lessThanOrEqualTo(root.get("birthdate"), userForFilterDto.getToDate());
        if(userForFilterDto.getToDate()!=null)criteriaBuilder.greaterThan(root.get("birthdate"), userForFilterDto.getFromDate());
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}

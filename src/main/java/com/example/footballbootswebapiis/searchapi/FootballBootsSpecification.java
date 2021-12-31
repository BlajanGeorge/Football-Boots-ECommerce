package com.example.footballbootswebapiis.searchapi;

import com.example.footballbootswebapiis.enumlayer.Brand;
import com.example.footballbootswebapiis.model.FootballBoots;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Locale;

@NoArgsConstructor
@AllArgsConstructor
public class FootballBootsSpecification implements Specification<FootballBoots> {
    private SearchCriteria criteria;

    @Override
    public Predicate toPredicate
            (Root<FootballBoots> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        if (criteria.getOperation().equalsIgnoreCase(">")) {
            return builder.greaterThanOrEqualTo(
                    root.get(criteria.getKey()), criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase("<")) {
            return builder.lessThanOrEqualTo(
                    root.get(criteria.getKey()), criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return builder.like(
                        root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
            } else {
                if (root.get(criteria.getKey()).getJavaType() == Brand.class) {
                    return builder.equal(root.get(criteria.getKey()), Brand.valueOf(criteria.getValue().toString().toUpperCase(Locale.ROOT)));
                } else {
                    return builder.equal(root.get(criteria.getKey()), criteria.getValue());
                }
            }
        }
        return null;
    }
}

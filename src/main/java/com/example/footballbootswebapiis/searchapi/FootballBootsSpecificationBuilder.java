package com.example.footballbootswebapiis.searchapi;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FootballBootsSpecificationBuilder {
    private final List<SearchCriteria> params;

    public FootballBootsSpecificationBuilder() {
        params = new ArrayList<>();
    }

    public FootballBootsSpecificationBuilder with(String key, String operation, Object value, String predicate) {
        params.add(new SearchCriteria(key, operation, value, predicate));
        return this;
    }

    public Specification build() {
        if (params.isEmpty()) {
            return null;
        }

        List<Specification> specs = params.stream()
                .map(FootballBootsSpecification::new)
                .collect(Collectors.toList());

        Specification result = specs.get(0);

        for (int i = 1; i < params.size(); i++) {
            result = params.get(i-1)
                    .isOrPredicate()
                    ? Specification.where(result)
                    .or(specs.get(i))
                    : Specification.where(result)
                    .and(specs.get(i));
        }
        return result;
    }
}

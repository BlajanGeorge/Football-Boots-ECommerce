package com.example.footballbootswebapiis.searchapi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchCriteria {
    private String key;
    private String operation;
    private Object value;
    private String predicate;

    public boolean isOrPredicate()
    {
        return this.predicate.equalsIgnoreCase(",");
    }
}

package com.example.footballbootswebapiis.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class FootballBootsAttributesUpdateResponse {
    private int id;
    private int size;
    private float price;
    private int quantity;
}

package com.example.footballbootswebapiis.model;

import com.example.footballbootswebapiis.enumlayer.Brand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FootballBootsDefaultSizeResponse {
    private int id;
    private String name;
    private Float price;
    private Integer quantity;
    private Integer size; //default size for response is 40
    private String description;
    private String brand;
}

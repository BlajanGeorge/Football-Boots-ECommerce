package com.example.footballbootswebapiis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateBasketEntryRequest {
    private int idUser;
    private int idBoots;
    private String name;
    private int size;
    private int price;
}

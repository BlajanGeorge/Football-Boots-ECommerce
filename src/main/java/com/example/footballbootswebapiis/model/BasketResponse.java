package com.example.footballbootswebapiis.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BasketResponse {
    private int idBasket;
    private int idUser;
    private int idBoots;
    private String name;
    private int size;
    private int price;
}

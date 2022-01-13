package com.example.footballbootswebapiis.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "basket")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idBasket;
    @Column(nullable = false)
    private int idUser;
    @Column(nullable = false)
    private int idBoots;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int size;
    @Column(nullable = false)
    private int price;

    public Basket(int idUser, int idBoots, String name, int size, int price) {
        this.idUser = idUser;
        this.idBoots = idBoots;
        this.name = name;
        this.size = size;
        this.price = price;
    }
}

package com.example.footballbootswebapiis.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "footbalbootsattributes")
@Validated
@EqualsAndHashCode
public class FootballBootsAttributes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private int size;
    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false)
    private float price;
    @ManyToMany(mappedBy = "footballBootsAttributesList")
    private List<FootballBoots> footballBootsSet = new ArrayList<>();

    public FootballBootsAttributes(int size, int quantity, float price) {
        this.size = size;
        this.quantity = quantity;
        this.price = price;
    }

}

package com.example.footballbootswebapiis.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "rating")
@Data
@NoArgsConstructor
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private BigDecimal rating;
    @Column
    private int bootsId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Rating(BigDecimal rating, int bootsId, User user) {
        this.rating = rating;
        this.bootsId = bootsId;
        this.user = user;
    }
}

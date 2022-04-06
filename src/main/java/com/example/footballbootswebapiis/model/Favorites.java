package com.example.footballbootswebapiis.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "favorites")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Favorites {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idFavorites;
    @Column
    private int bootsId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Favorites(int bootsId, User user) {
        this.bootsId = bootsId;
        this.user = user;
    }
}

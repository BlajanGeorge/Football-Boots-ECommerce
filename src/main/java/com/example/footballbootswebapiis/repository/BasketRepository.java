package com.example.footballbootswebapiis.repository;

import com.example.footballbootswebapiis.model.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Integer> {
    List<Basket> getByIdUser(int id);

    void deleteAllByIdUser(int id);

    void deleteByIdBasket(int idBasket);
}

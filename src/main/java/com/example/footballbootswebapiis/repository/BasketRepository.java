package com.example.footballbootswebapiis.repository;

import com.example.footballbootswebapiis.model.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BasketRepository extends JpaRepository<Basket, Integer> {
    List<Basket> getByIdUser(int id);
}

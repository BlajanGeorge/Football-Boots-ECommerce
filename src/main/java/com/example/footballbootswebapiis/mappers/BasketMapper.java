package com.example.footballbootswebapiis.mappers;

import com.example.footballbootswebapiis.model.Basket;
import com.example.footballbootswebapiis.model.BasketResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BasketMapper {

    public static BasketResponse mapFromBasketModelToBasketResponse(Basket basket) {
        return new BasketResponse(basket.getIdBasket(), basket.getIdUser(), basket.getIdBoots(), basket.getName(), basket.getSize(), basket.getPrice());
    }
}

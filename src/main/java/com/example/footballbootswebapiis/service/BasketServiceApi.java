package com.example.footballbootswebapiis.service;

import com.example.footballbootswebapiis.dto.BasketResponse;
import com.example.footballbootswebapiis.dto.CreateBasketEntryRequest;

import java.util.List;

public interface BasketServiceApi {

    void createEntryInBasket(final CreateBasketEntryRequest createBasketEntryRequest);

    List<BasketResponse> getBasketByUserId(final int id);

    void deleteEntryById(final int id);

    int getTotalPrice(final int id);

    void cleanBasketByUserId(int id);
}

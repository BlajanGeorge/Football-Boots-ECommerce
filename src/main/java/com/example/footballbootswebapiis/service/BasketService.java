package com.example.footballbootswebapiis.service;

import com.example.footballbootswebapiis.mappers.BasketMapper;
import com.example.footballbootswebapiis.model.Basket;
import com.example.footballbootswebapiis.dto.BasketResponse;
import com.example.footballbootswebapiis.dto.CreateBasketEntryRequest;
import com.example.footballbootswebapiis.repository.BasketRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class BasketService implements BasketServiceApi {
    private final BasketRepository basketRepository;

    public BasketService(final BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    @Transactional
    public void createEntryInBasket(final CreateBasketEntryRequest createBasketEntryRequest) {
        this.basketRepository.save(new Basket(createBasketEntryRequest.getIdUser(), createBasketEntryRequest.getIdBoots(), createBasketEntryRequest.getName(),
                createBasketEntryRequest.getSize(), createBasketEntryRequest.getPrice()));
    }

    public List<BasketResponse> getBasketByUserId(final int id) {
        List<BasketResponse> basketResponses = new ArrayList<>();
        for (Basket basket : this.basketRepository.getByIdUser(id)) {
            basketResponses.add(BasketMapper.mapFromBasketModelToBasketResponse(basket));
        }
        return basketResponses;
    }

    @Transactional
    public void deleteEntryById(final int id) {
        this.basketRepository.deleteById(id);
    }

    public int getTotalPrice(final int id) {
        AtomicInteger totalPrice = new AtomicInteger();
        this.getBasketByUserId(id).forEach(item -> totalPrice.addAndGet(item.getPrice()));
        return totalPrice.get();
    }

    @Transactional
    public void cleanBasketByUserId(int id) {
        this.basketRepository.deleteAllByIdUser(id);
    }

}

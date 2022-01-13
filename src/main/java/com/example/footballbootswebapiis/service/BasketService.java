package com.example.footballbootswebapiis.service;

import com.example.footballbootswebapiis.model.Basket;
import com.example.footballbootswebapiis.model.CreateBasketEntryRequest;
import com.example.footballbootswebapiis.repository.BasketRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class BasketService {
    private BasketRepository basketRepository;

    public BasketService(BasketRepository basketRepository)
    {
        this.basketRepository = basketRepository;
    }

    public void createEntryInBasket(CreateBasketEntryRequest createBasketEntryRequest)
    {
        this.basketRepository.save(new Basket(createBasketEntryRequest.getIdUser(), createBasketEntryRequest.getIdBoots(), createBasketEntryRequest.getName(),
                createBasketEntryRequest.getSize(), createBasketEntryRequest.getPrice()));
    }

    public List<Basket> getBasketByUserId(int id)
    {
        return this.basketRepository.getByIdUser(id);
    }

    public void deleteEntryById(int id)
    {
        this.basketRepository.deleteById(id);
    }

    public int getTotalPrice(int id)
    {
        AtomicInteger totalPrice = new AtomicInteger();
        this.getBasketByUserId(id).stream().forEach(item -> totalPrice.addAndGet(item.getPrice()));
        return totalPrice.get();
    }

}

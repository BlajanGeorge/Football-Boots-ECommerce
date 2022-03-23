package com.example.footballbootswebapiis.controller;

import com.example.footballbootswebapiis.model.BasketResponse;
import com.example.footballbootswebapiis.model.CreateBasketEntryRequest;
import com.example.footballbootswebapiis.service.BasketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/basket")
@CrossOrigin
@Validated
public class BasketController {

    private final BasketService basketService;

    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @PostMapping(value = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createEntryInBasket(@RequestBody CreateBasketEntryRequest createBasketEntryRequest) {
        this.basketService.createEntryInBasket(createBasketEntryRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/totalPrice/{id}")
    public ResponseEntity getTotalPrice(@PathVariable int id)
    {
        return new ResponseEntity(this.basketService.getTotalPrice(id), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<BasketResponse>> getBasketByUserId(@PathVariable int id) {
        return new ResponseEntity<>(this.basketService.getBasketByUserId(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteEntryById(@PathVariable int id) {
        this.basketService.deleteEntryById(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}

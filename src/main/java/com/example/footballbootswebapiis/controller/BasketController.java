package com.example.footballbootswebapiis.controller;

import com.example.footballbootswebapiis.dto.BasketResponse;
import com.example.footballbootswebapiis.dto.CreateBasketEntryRequest;
import com.example.footballbootswebapiis.service.BasketService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class BasketController {

    private final BasketService basketService;

    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @PostMapping(value = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createEntryInBasket(@RequestBody CreateBasketEntryRequest createBasketEntryRequest) {
        log.info("Create basket request received for user with id {}.", createBasketEntryRequest.getIdUser());
        this.basketService.createEntryInBasket(createBasketEntryRequest);
        log.info("Basket created for user with id {}.", createBasketEntryRequest.getIdUser());
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/totalPrice/{id}")
    public ResponseEntity getTotalPrice(@PathVariable int id) {
        log.info("Total price will be computed.");
        return new ResponseEntity(this.basketService.getTotalPrice(id), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<BasketResponse>> getBasketByUserId(@PathVariable int id) {
        log.info("Get basket by user id received for user with id {}.", id);
        return new ResponseEntity<>(this.basketService.getBasketByUserId(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteEntryById(@PathVariable int id) {
        log.info("Delete entry in basket by id received for id {}.", id);
        this.basketService.deleteEntryById(id);
        log.info("Entry in basket deleted for id {}.", id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity cleanBasketByUserId(@PathVariable int id) {
        log.info("Clean basket request received for user with id {}.", id);
        this.basketService.cleanBasketByUserId(id);
        log.info("Clean basket completed.");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

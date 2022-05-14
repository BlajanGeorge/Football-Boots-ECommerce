package com.example.footballbootswebapiis.service;

import com.example.footballbootswebapiis.bill.BillMaker;
import com.example.footballbootswebapiis.model.Basket;
import com.example.footballbootswebapiis.model.User;
import com.example.footballbootswebapiis.repository.BasketRepository;
import com.example.footballbootswebapiis.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Service;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class OrderService implements OrderServiceApi {

    private final BasketRepository basketRepository;
    private final UserRepository userRepository;

    public OrderService(final BasketRepository basketRepository, final UserRepository userRepository) {
        this.basketRepository = basketRepository;
        this.userRepository = userRepository;
    }

    public void createBill(final int userId) {
        log.info("Will create xml bill for user {}.", userId);
        List<Basket> basketList = basketRepository.getByIdUser(userId);
        Optional<User> user = userRepository.findById(userId);
        AtomicInteger totalPrice = new AtomicInteger();
        basketList.forEach(item -> totalPrice.addAndGet(item.getPrice()));
        try {
            BillMaker.createXmlBill(basketList, totalPrice.get(), user.get().getFirstName());
        } catch (ParserConfigurationException | TransformerException e) {
            log.error("Xml bill creation for user {} failed due to ..", userId, e);
        }
        log.info("Will create json bill for user {}.", userId);
        try {
            BillMaker.createJsonBill(basketList, totalPrice.get(), user.get().getFirstName());
        } catch (JSONException | IOException e) {
            log.error("Json bill creation for user {} failed due to ..", userId, e);
        }
        log.info("Will create pdf bill for user {}.", userId);
        try {
            BillMaker.createPdfBill(basketList, totalPrice.get(), user.get().getFirstName());
        } catch (IOException e) {
            log.error("Pdf bill creation for user {} failed due to ..", userId, e);
        }
    }
}

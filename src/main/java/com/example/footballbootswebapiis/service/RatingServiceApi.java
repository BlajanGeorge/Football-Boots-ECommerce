package com.example.footballbootswebapiis.service;

import com.example.footballbootswebapiis.model.Rating;

import java.math.BigDecimal;

public interface RatingServiceApi {
    Rating getRatingByUserAndBootsId(int userId, int bootsId);

    void createRating(Rating rating);

    void updateRating(Rating rating);

    BigDecimal getAvgForBoots(int bootsId);
}

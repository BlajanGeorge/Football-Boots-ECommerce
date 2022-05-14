package com.example.footballbootswebapiis.service;

import com.example.footballbootswebapiis.model.Rating;
import com.example.footballbootswebapiis.repository.RatingRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class RatingService implements RatingServiceApi {

    private final RatingRepository ratingRepository;

    public RatingService(final RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Override
    public Rating getRatingByUserAndBootsId(final int userId, final int bootsId) {
        return ratingRepository.findByBootsIdAndUserId(bootsId, userId);
    }

    @Override
    public void createRating(Rating rating) {
        ratingRepository.save(rating);
    }

    @Override
    public void updateRating(Rating rating) {
        Rating rating1 = ratingRepository.findByBootsIdAndUserId(rating.getBootsId(), rating.getUser().getId());
        if (rating1 != null) {
            rating1.setRating(rating.getRating());
            ratingRepository.save(rating1);
        }
    }

    @Override
    public BigDecimal getAvgForBoots(int bootsId) {
        List<Rating> ratings = ratingRepository.findAllByBootsId(bootsId);
        if (ratings.isEmpty()) {
            return BigDecimal.valueOf(0.0);
        }
        final BigDecimal[] sum = {BigDecimal.valueOf(0)};

        ratings.forEach(rating -> sum[0] = sum[0].add(rating.getRating()));
        sum[0] = sum[0].divide(BigDecimal.valueOf(ratings.size()));

        return sum[0];
    }
}

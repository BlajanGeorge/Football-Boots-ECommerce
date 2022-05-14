package com.example.footballbootswebapiis.mappers;

import com.example.footballbootswebapiis.dto.RatingDto;
import com.example.footballbootswebapiis.model.Rating;
import com.example.footballbootswebapiis.model.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RatingMapper {

    public static Rating mapFromDtoToModel(RatingDto ratingDto, User user) {
        return new Rating(ratingDto.getRating(), ratingDto.getBootsId(), user);
    }

    public static RatingDto mapFromModelToDto(Rating rating) {
        if (rating != null) {
            return new RatingDto(rating.getBootsId(), rating.getUser().getId(), rating.getRating());
        }

        return null;
    }
}

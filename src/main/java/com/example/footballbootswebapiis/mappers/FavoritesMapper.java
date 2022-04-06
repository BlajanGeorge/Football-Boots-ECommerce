package com.example.footballbootswebapiis.mappers;

import com.example.footballbootswebapiis.dto.FavoritesDto;
import com.example.footballbootswebapiis.model.Favorites;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FavoritesMapper {

    public static FavoritesDto mapFromModelToResponse(Favorites favorites) {
        return new FavoritesDto(favorites.getBootsId(), favorites.getUser().getId());
    }
}

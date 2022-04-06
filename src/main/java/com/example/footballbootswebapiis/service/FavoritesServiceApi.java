package com.example.footballbootswebapiis.service;

import com.example.footballbootswebapiis.dto.FavoritesDto;
import com.example.footballbootswebapiis.dto.FavoritesResponseForGet;

import javax.naming.LimitExceededException;
import java.util.List;

public interface FavoritesServiceApi {

    FavoritesDto create(FavoritesDto favoritesDto) throws LimitExceededException;

    boolean isFavorite(int userId, int bootsId);

    List<FavoritesResponseForGet> getAllFavoritesForUserId(int userId);

    void deleteById(int id);
}

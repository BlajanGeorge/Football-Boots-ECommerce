package com.example.footballbootswebapiis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FavoritesResponseForGet {
    private int favoritesId;
    private int bootsId;
    private String bootsName;
}

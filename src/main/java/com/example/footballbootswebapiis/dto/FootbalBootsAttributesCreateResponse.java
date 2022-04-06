package com.example.footballbootswebapiis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FootbalBootsAttributesCreateResponse {
    private Integer size;
    private Integer price;
    private Integer quantity;
}

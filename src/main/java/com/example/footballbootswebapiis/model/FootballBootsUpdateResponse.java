package com.example.footballbootswebapiis.model;

import com.example.footballbootswebapiis.enumlayer.Brand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class FootballBootsUpdateResponse {
    private int id;
    private String name;
    private String description;
    private String brand;
    private List<FootballBootsAttributesUpdateResponse> footballBootsAttributesUpdateResponseList;
}

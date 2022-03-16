package com.example.footballbootswebapiis.model;

import com.example.footballbootswebapiis.enumlayer.Brand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FootballBootsCreateResponse {
    private String name;
    private String description;
    private Brand brand;
    private String photoPath;
    private String bigPhotoPath;
    private List<FootbalBootsAttributesCreateResponse> footbalBootsAttributesCreateResponseList;
}

package com.example.footballbootswebapiis.dto;

import com.example.footballbootswebapiis.dto.FootballBootsAttributesUpdateResponse;
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

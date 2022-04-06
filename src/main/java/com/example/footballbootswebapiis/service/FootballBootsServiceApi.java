package com.example.footballbootswebapiis.service;

import com.example.footballbootswebapiis.dto.FootballBootsAttributesUpdateRequest;
import com.example.footballbootswebapiis.dto.FootballBootsCreateResponse;
import com.example.footballbootswebapiis.dto.FootballBootsUpdateRequest;
import com.example.footballbootswebapiis.model.FootballBoots;

import java.util.List;
import java.util.Optional;

public interface FootballBootsServiceApi {

    List<FootballBoots> getFootballBoots();

    List<FootballBoots> getAllFootballBootsWithFilter(List<String> brand, List<Integer> size, Integer minPrice, Integer maxPrice, Integer sort);

    FootballBootsCreateResponse createFootballBoots(FootballBoots footballBoots);

    void deleteFootballBootsById(int id);

    FootballBoots updateFootballBootsById(int id, FootballBootsUpdateRequest footballBootsUpdateRequest);

    FootballBoots updateFootballBootsByIdAndSize(int id, int size, FootballBootsAttributesUpdateRequest footballBootsAttributesUpdateRequest);

    Optional<FootballBoots> getBootsById(int id);
}

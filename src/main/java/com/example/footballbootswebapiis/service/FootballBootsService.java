package com.example.footballbootswebapiis.service;

import com.example.footballbootswebapiis.enumlayer.Brand;
import com.example.footballbootswebapiis.exceptions.EntityNotFoundException;
import com.example.footballbootswebapiis.mappers.FootballBootsMapper;
import com.example.footballbootswebapiis.model.*;
import com.example.footballbootswebapiis.repository.BasketRepository;
import com.example.footballbootswebapiis.repository.FootballBootsAttributesRepository;
import com.example.footballbootswebapiis.repository.FootballBootsRepository;
import com.example.footballbootswebapiis.searchapi.FootballBootsAttributesSpecificationBuilder;
import com.example.footballbootswebapiis.searchapi.FootballBootsSpecificationBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class FootballBootsService {
    private final FootballBootsRepository footballBootsRepository;
    private final FootballBootsAttributesRepository footballBootsAttributesRepository;
    private final BasketService basketService;

    public FootballBootsService(final FootballBootsRepository footballBootsRepository, final FootballBootsAttributesRepository footballBootsAttributesRepository, final BasketService basketService) {
        this.footballBootsAttributesRepository = footballBootsAttributesRepository;
        this.footballBootsRepository = footballBootsRepository;
        this.basketService = basketService;
    }

    public List<FootballBoots> getFootballBoots() {
        return this.footballBootsRepository.findAll();
    }

    public List<FootballBoots> getAllFootballBootsWithFilter(String search, String search2, String order) {

        Set<FootballBoots> footballBootsSetFromSearchOne = new HashSet<>();
        Set<FootballBoots> footballBootsSetFromSearchTwo = new HashSet<>();

        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?)(,|;)");

        if (!StringUtils.isBlank(search)) {
            FootballBootsSpecificationBuilder builder = new FootballBootsSpecificationBuilder();
            Matcher matcher = pattern.matcher(search);
            while (matcher.find()) {
                builder.with(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4));
            }
            Specification<FootballBoots> spec = builder.build();
            footballBootsSetFromSearchOne.addAll(this.footballBootsRepository.findAll(spec));
        } else {
            footballBootsSetFromSearchOne.addAll(this.footballBootsRepository.findAll());
        }
        if (!StringUtils.isBlank(search2)) {
            FootballBootsAttributesSpecificationBuilder builder = new FootballBootsAttributesSpecificationBuilder();
            Matcher matcher = pattern.matcher(search2);
            while (matcher.find()) {
                builder.with(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4));
            }
            Specification<FootballBootsAttributes> spec = builder.build();
            List<FootballBootsAttributes> footballBootsAttributes = this.footballBootsAttributesRepository.findAll(spec);
            footballBootsAttributes.stream().forEach(attribute -> footballBootsSetFromSearchTwo.add(this.footballBootsRepository.findById(attribute.getFootballBootsSet().get(0).getId()).get()));
        } else {
            List<FootballBootsAttributes> footballBootsAttributes = this.footballBootsAttributesRepository.findAll();
            footballBootsAttributes.stream().forEach(attribute -> footballBootsSetFromSearchTwo.add(this.footballBootsRepository.findById(attribute.getFootballBootsSet().get(0).getId()).get()));
        }
        footballBootsSetFromSearchOne.retainAll(footballBootsSetFromSearchTwo);
        List<FootballBoots> footballBoots = new ArrayList<>(footballBootsSetFromSearchOne);
        return footballBoots;
    }

    public FootballBootsCreateResponse createFootballBoots(FootballBoots footballBoots) {
        footballBoots.getFootballBootsAttributesList().forEach(attribute ->
        {
            FootballBootsAttributes footballBootsAttributes = this.footballBootsAttributesRepository.save(attribute);
            footballBootsAttributes.getFootballBootsSet().add(footballBoots);
        });
        return FootballBootsMapper.mapFromModelToCreateResponse(this.footballBootsRepository.save(footballBoots));
    }

    public void deleteFootballBootsById(int id) {
        Optional<FootballBoots> footballBootsOptional = this.footballBootsRepository.findById(id);
        FootballBoots footballBoots = footballBootsOptional.orElseThrow(() -> new EntityNotFoundException(String.format("Footbal boots with id %d doesn't exist.", id)));
        footballBoots.getFootballBootsAttributesList().forEach(attribute -> this.footballBootsAttributesRepository.deleteById(attribute.getId()));
        List<Basket> baskets = this.basketService.getAll();
        for (Basket basket : baskets) {
            if (basket.getIdBoots() == id) {
                this.basketService.deleteEntryById(basket.getIdBasket());
            }
        }
        this.footballBootsRepository.deleteById(id);
    }

    public FootballBoots updateFootballBootsById(int id, FootballBootsUpdateRequest footballBootsUpdateRequest) {
        Optional<FootballBoots> footballBootsOptional = this.footballBootsRepository.findById(id);
        FootballBoots footballBoots = footballBootsOptional.orElseThrow(() -> new EntityNotFoundException(String.format("Footbal boots with id %d doesn't exist.", id)));
        if (!StringUtils.isBlank(footballBootsUpdateRequest.getName())) {
            footballBoots.setName(footballBootsUpdateRequest.getName());
        }
        if (!StringUtils.isBlank(footballBootsUpdateRequest.getDescription())) {
            footballBoots.setDescription(footballBootsUpdateRequest.getDescription());
        }
        if (footballBootsUpdateRequest.getBrand() != null) {
            footballBoots.setBrand(Brand.valueOf(footballBootsUpdateRequest.getBrand()));
        }

        return this.footballBootsRepository.save(footballBoots);
    }

    public FootballBoots updateFootballBootsByIdAndSize(int id, int size, FootballBootsAttributesUpdateRequest footballBootsAttributesUpdateRequest) {
        Optional<FootballBoots> footballBootsOptional = this.footballBootsRepository.findById(id);
        FootballBoots footballBoots = footballBootsOptional.orElseThrow(() -> new EntityNotFoundException(String.format("Footbal boots with id %d doesn't exist.", id)));
        List<FootballBootsAttributes> footballBootsAttributes = footballBoots.getFootballBootsAttributesList().stream().filter(attribute -> attribute.getSize() == size).collect(Collectors.toList());

        if (footballBootsAttributesUpdateRequest.getPrice() != null && footballBootsAttributesUpdateRequest.getPrice() >= 0) {
            footballBootsAttributes.get(0).setPrice(footballBootsAttributesUpdateRequest.getPrice());
        }
        if (footballBootsAttributesUpdateRequest.getQuantity() != null && footballBootsAttributesUpdateRequest.getQuantity() >= 0) {
            footballBootsAttributes.get(0).setQuantity(footballBootsAttributesUpdateRequest.getQuantity());
        }

        this.footballBootsAttributesRepository.save(footballBootsAttributes.get(0));
        return this.footballBootsRepository.save(footballBoots);
    }

    public Optional<FootballBoots> getBootsById(int id) {
        return this.footballBootsRepository.findById(id);
    }
}

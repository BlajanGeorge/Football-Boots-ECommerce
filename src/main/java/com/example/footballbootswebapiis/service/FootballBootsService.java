package com.example.footballbootswebapiis.service;

import com.example.footballbootswebapiis.dto.FootballBootsAttributesUpdateRequest;
import com.example.footballbootswebapiis.dto.FootballBootsCreateResponse;
import com.example.footballbootswebapiis.dto.FootballBootsDefaultSizeResponse;
import com.example.footballbootswebapiis.dto.FootballBootsUpdateRequest;
import com.example.footballbootswebapiis.enumlayer.Brand;
import com.example.footballbootswebapiis.exceptions.EntityNotFoundException;
import com.example.footballbootswebapiis.mappers.FootballBootsMapper;
import com.example.footballbootswebapiis.model.Basket;
import com.example.footballbootswebapiis.model.FootballBoots;
import com.example.footballbootswebapiis.model.FootballBootsAttributes;
import com.example.footballbootswebapiis.repository.BasketRepository;
import com.example.footballbootswebapiis.repository.FavoritesRepository;
import com.example.footballbootswebapiis.repository.FootballBootsAttributesRepository;
import com.example.footballbootswebapiis.repository.FootballBootsRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FootballBootsService implements FootballBootsServiceApi {
    private final FootballBootsRepository footballBootsRepository;
    private final FootballBootsAttributesRepository footballBootsAttributesRepository;
    private final BasketRepository basketRepository;
    private final FavoritesRepository favoritesRepository;

    public FootballBootsService(final FootballBootsRepository footballBootsRepository,
                                final FootballBootsAttributesRepository footballBootsAttributesRepository,
                                final BasketRepository basketRepository,
                                final FavoritesRepository favoritesRepository) {
        this.footballBootsAttributesRepository = footballBootsAttributesRepository;
        this.footballBootsRepository = footballBootsRepository;
        this.basketRepository = basketRepository;
        this.favoritesRepository = favoritesRepository;
    }

    public List<FootballBoots> getFootballBoots() {
        return this.footballBootsRepository.findAll();
    }


    public List<FootballBoots> getAllFootballBootsWithFilter(List<String> brands, List<Integer> size, Integer minPrice, Integer maxPrice, Integer sort) {
        List<FootballBoots> result = new ArrayList<>();
        List<FootballBoots> initialList = this.footballBootsRepository.findAll();

        for (FootballBoots footballBoots : initialList) {
            boolean keep = false;
            if (brands.contains(footballBoots.getBrand().name())) {
                keep = true;
                for (Integer size2 : size) {
                    if (footballBoots.getQuantityBySize(size2) == 0) {
                        keep = false;
                        break;
                    }
                }
                if (keep) {
                    List<FootballBootsAttributes> footballBootsAttributes = footballBoots.getFootballBootsAttributesList();
                    for (FootballBootsAttributes footballBootsAttributes1 : footballBootsAttributes) {
                        if (footballBootsAttributes1.getSize() == 40 && (footballBootsAttributes1.getPrice() < minPrice || footballBootsAttributes1.getPrice() > maxPrice)) {
                            keep = false;
                            break;
                        }
                    }
                }
            }
            if (keep) {
                result.add(footballBoots);
            }
        }
        if (sort != null && sort == 1) {
            result.sort((o1, o2) -> {
                FootballBootsDefaultSizeResponse footballBootsDefaultSizeResponse = FootballBootsMapper.mapFromModelToDefaultResponse(o1);
                FootballBootsDefaultSizeResponse footballBootsDefaultSizeResponse2 = FootballBootsMapper.mapFromModelToDefaultResponse(o2);
                return footballBootsDefaultSizeResponse.getPrice() - footballBootsDefaultSizeResponse2.getPrice();
            });
        }

        if (sort != null && sort == 0) {
            result.sort((o1, o2) -> {
                FootballBootsDefaultSizeResponse footballBootsDefaultSizeResponse = FootballBootsMapper.mapFromModelToDefaultResponse(o1);
                FootballBootsDefaultSizeResponse footballBootsDefaultSizeResponse2 = FootballBootsMapper.mapFromModelToDefaultResponse(o2);
                return footballBootsDefaultSizeResponse2.getPrice() - footballBootsDefaultSizeResponse.getPrice();
            });
        }
        return result;
    }

    @Transactional
    public FootballBootsCreateResponse createFootballBoots(FootballBoots footballBoots) {
        footballBoots.getFootballBootsAttributesList().forEach(attribute ->
        {
            FootballBootsAttributes footballBootsAttributes = this.footballBootsAttributesRepository.save(attribute);
            footballBootsAttributes.getFootballBootsSet().add(footballBoots);
        });
        return FootballBootsMapper.mapFromModelToCreateResponse(this.footballBootsRepository.save(footballBoots));
    }

    @Transactional
    public void deleteFootballBootsById(int id) {
        Optional<FootballBoots> footballBootsOptional = this.footballBootsRepository.findById(id);
        FootballBoots footballBoots = footballBootsOptional.orElseThrow(() -> new EntityNotFoundException(String.format("Football boots with id %d doesn't exist.", id)));
        footballBoots.getFootballBootsAttributesList().forEach(attribute -> this.footballBootsAttributesRepository.deleteById(attribute.getId()));
        List<Basket> baskets = this.basketRepository.findAll();
        for (Basket basket : baskets) {
            if (basket.getIdBoots() == id) {
                this.basketRepository.deleteByIdBasket(basket.getIdBasket());
            }
        }
        this.favoritesRepository.deleteAllByBootsId(id);
        this.footballBootsRepository.deleteById(id);
    }

    @Transactional
    public FootballBoots updateFootballBootsById(int id, FootballBootsUpdateRequest footballBootsUpdateRequest) {
        Optional<FootballBoots> footballBootsOptional = this.footballBootsRepository.findById(id);
        FootballBoots footballBoots = footballBootsOptional.orElseThrow(() -> new EntityNotFoundException(String.format("Football boots with id %d doesn't exist.", id)));
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

    @Transactional
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

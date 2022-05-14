package com.example.footballbootswebapiis.controller;

import com.example.footballbootswebapiis.dto.*;
import com.example.footballbootswebapiis.exceptions.EntityNotFoundException;
import com.example.footballbootswebapiis.mappers.FootballBootsMapper;
import com.example.footballbootswebapiis.model.*;
import com.example.footballbootswebapiis.service.FootballBootsService;
import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

@RestController
@Validated
@CrossOrigin
@RequestMapping("/boots")
@Slf4j
public class FootballBootsController {
    private final FootballBootsService footballBootsService;

    public FootballBootsController(final FootballBootsService footballBootsService) {
        this.footballBootsService = footballBootsService;
    }

    @GetMapping
    public ResponseEntity<List<FootballBootsDefaultSizeResponse>> getAllFootballBoots() {
        log.info("Request for get all football boots received.");
        return new ResponseEntity<>(FootballBootsMapper.mapFromDefaultResponseToArrayResponse(this.footballBootsService.getFootballBoots()), HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<FootballBootsDefaultSizeResponse>> getAllFootballBootsWithFilter(@RequestParam(value = "brand") List<String> brand,
                                                                                                @RequestParam(value = "size") List<Integer> size,
                                                                                                @RequestParam(value = "minPrice") Integer minPrice,
                                                                                                @RequestParam(value = "maxPrice") Integer maxPrice,
                                                                                                @RequestParam(value = "sorting", required = false) Integer sort) {
        log.info("Request for get football boots with filtering received. Filter {}", brand.toString() + " " + size.toString() + " " + minPrice + " " + maxPrice);
        return new ResponseEntity<>(FootballBootsMapper.mapFromDefaultResponseToArrayResponse(this.footballBootsService.getAllFootballBootsWithFilter(brand, size, minPrice, maxPrice, sort)), HttpStatus.OK);
    }

    @GetMapping("/{id}/{size}")
    public ResponseEntity<FootballBootsDefaultSizeResponse> getFootballBootsByIdAndSize(@PathVariable @Min(value = 1, message = "Id can't be negative.") int id,
                                                                                        @PathVariable @Range(min = 35, max = 45, message = "Boots size must be between 35 and 45.") int size) {
        log.info("Get football boots by id and size received. size {} , id {}", size, id);
        Optional<FootballBoots> footballBootsOptional = this.footballBootsService.getBootsById(id);
        return footballBootsOptional.map(footballBoots -> new ResponseEntity<>(FootballBootsMapper.mapFromModelToSizeResponse(footballBoots, size), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<FootballBootsCreateResponse> createFootballBoots(@RequestBody @Valid FootballBootsCreateRequest footballBootsCreateRequest) {
        log.info("Create football boots request received. Data {}, {}", footballBootsCreateRequest.getName(), footballBootsCreateRequest.getBrand());
        return new ResponseEntity<>
                (this.footballBootsService.createFootballBoots(FootballBootsMapper.mapFromCreateRequestToModel(footballBootsCreateRequest)),
                        HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteFootballBootsById(@PathVariable @Min(value = 1, message = "Id can't be negative.") int id) {
        log.info("Delete football boots by id request received. Id {}", id);
        try {
            this.footballBootsService.deleteFootballBootsById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            log.warn("Delete request failed for id {}.", id);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateFootballBootsById(@RequestBody @Valid FootballBootsUpdateRequest footballBootsUpdateRequest, @PathVariable @Min(value = 1, message = "Id can't be negative.") int id) {
        log.info("Update football boots by id request received. Id {}", id);
        try {
            return new ResponseEntity(FootballBootsMapper.mapFromModelToUpdateResponse(this.footballBootsService.updateFootballBootsById(id, footballBootsUpdateRequest)), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            log.warn("Update request failed for id {}.", id);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}/{size}")
    public ResponseEntity updateFootballBootsByIdAndSize(@RequestBody @Valid FootballBootsAttributesUpdateRequest footballBootsAttributesUpdateRequest,
                                                         @PathVariable @Min(value = 1, message = "Id can't be negative.") int id,
                                                         @PathVariable @Range(min = 35, max = 45, message = "Boots size must be between 35 and 45.") int size) {
        log.info("Update football boots by id and size received. Id {} , size {}", id, size);
        try {
            return new ResponseEntity(FootballBootsMapper.mapFromModelToUpdateResponse(this.footballBootsService.updateFootballBootsByIdAndSize(id, size, footballBootsAttributesUpdateRequest)), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            log.warn("Update football boots by id and size failed. Id {}, size {}.", id, size);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}

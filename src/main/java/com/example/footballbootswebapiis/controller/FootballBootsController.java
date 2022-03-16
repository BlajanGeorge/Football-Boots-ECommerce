package com.example.footballbootswebapiis.controller;

import com.example.footballbootswebapiis.exceptions.EntityNotFoundException;
import com.example.footballbootswebapiis.mappers.FootballBootsMapper;
import com.example.footballbootswebapiis.model.*;
import com.example.footballbootswebapiis.service.FootballBootsService;
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
public class FootballBootsController {
    private FootballBootsService footballBootsService;

    public FootballBootsController(FootballBootsService footballBootsService) {
        this.footballBootsService = footballBootsService;
    }

    @GetMapping
    public ResponseEntity<List<FootballBootsDefaultSizeResponse>> getAllFootballBoots() {
        return new ResponseEntity<>(FootballBootsMapper.mapFromDefaultResponseToArrayResponse(this.footballBootsService.getFootballBoots()), HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<FootballBootsDefaultSizeResponse>> getAllFootballBootsWithFilter(@RequestParam(value = "search", required = false) String search,
                                                                                                @RequestParam(value = "search2", required = false) String search2,
                                                                                                @RequestParam(value = "order", required = false) String order)
    {
        return new ResponseEntity<>(FootballBootsMapper.mapFromDefaultResponseToArrayResponse(this.footballBootsService.getAllFootballBootsWithFilter(search, search2, order)), HttpStatus.OK);
    }

    @GetMapping("/{id}/{size}")
    public ResponseEntity<FootballBootsDefaultSizeResponse> getFootballBootsByIdAndSize(@PathVariable @Min(value = 1, message = "Id can't be negative.") int id,
                                                                                        @PathVariable @Range(min = 35, max = 45, message = "Boots size must be between 35 and 45.") int size) {
        Optional<FootballBoots> footballBootsOptional = this.footballBootsService.getBootsById(id);
        if (footballBootsOptional.isPresent()) {
            return new ResponseEntity<>(FootballBootsMapper.mapFromModelToSizeResponse(footballBootsOptional.get(), size), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<FootballBootsCreateResponse> createFootballBoots(@RequestBody @Valid FootballBootsCreateRequest footballBootsCreateRequest) {
        return new ResponseEntity<>
                (this.footballBootsService.createFootballBoots(FootballBootsMapper.mapFromCreateRequestToModel(footballBootsCreateRequest)),
                        HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteFootballBootsById(@PathVariable @Min(value = 1, message = "Id can't be negative.") int id) {
        try {
            this.footballBootsService.deleteFootballBootsById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateFootballBootsById(@RequestBody @Valid FootballBootsUpdateRequest footballBootsUpdateRequest, @PathVariable @Min(value = 1, message = "Id can't be negative.") int id) {
        try {
            return new ResponseEntity(FootballBootsMapper.mapFromModelToUpdateResponse(this.footballBootsService.updateFootballBootsById(id, footballBootsUpdateRequest)), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}/{size}")
    public ResponseEntity updateFootballBootsByIdAndSize(@RequestBody @Valid FootballBootsAttributesUpdateRequest footballBootsAttributesUpdateRequest,
                                                         @PathVariable @Min(value = 1, message = "Id can't be negative.") int id,
                                                         @PathVariable @Range(min = 35, max = 45, message = "Boots size must be between 35 and 45.") int size) {
        try {
            return new ResponseEntity(FootballBootsMapper.mapFromModelToUpdateResponse(this.footballBootsService.updateFootballBootsByIdAndSize(id, size, footballBootsAttributesUpdateRequest)), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}

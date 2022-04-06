package com.example.footballbootswebapiis.controller;

import com.example.footballbootswebapiis.dto.FavoritesDto;
import com.example.footballbootswebapiis.dto.FavoritesResponseForGet;
import com.example.footballbootswebapiis.exceptions.EntityNotFoundException;
import com.example.footballbootswebapiis.service.FavoritesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.LimitExceededException;
import java.util.List;

@RestController
@RequestMapping("/favorites")
@Slf4j
@CrossOrigin
public class FavoritesController {
    private final FavoritesService favoritesService;

    public FavoritesController(FavoritesService favoritesService) {
        this.favoritesService = favoritesService;
    }

    @GetMapping("/isFavorites/{userId}/{bootsId}")
    public ResponseEntity<Boolean> isFavoriteByUserIdAndBootsId(@PathVariable int userId, @PathVariable int bootsId) {
        log.info("Is favorite request received for user id {} and boots id {}.", userId, bootsId);
        return new ResponseEntity<>(this.favoritesService.isFavorite(userId, bootsId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<FavoritesDto> createFavorites(@RequestBody FavoritesDto favoritesDto) {
        log.info("Create favorites request received for user id {} and boots id {}.", favoritesDto.getUserId(), favoritesDto.getBootsId());
        try {
            return new ResponseEntity<>(this.favoritesService.create(favoritesDto), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            log.warn("Create request failed for user id {} and boots id {}.", favoritesDto.getUserId(), favoritesDto.getBootsId());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (LimitExceededException e) {
            log.warn("Create request failed for user id {} and boots id {}.", favoritesDto.getUserId(), favoritesDto.getBootsId());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<FavoritesResponseForGet>> getFavoritesForUserId(@PathVariable int userId) {
        log.info("Get all favorites request received for user id {}.", userId);
        return new ResponseEntity<>(this.favoritesService.getAllFavoritesForUserId(userId), HttpStatus.OK);
    }

    @DeleteMapping("/{favoritesId}")
    public ResponseEntity deleteById(@PathVariable int favoritesId) {
        log.info("Delete favorites by id request received for id {}.", favoritesId);
        this.favoritesService.deleteById(favoritesId);
        return new ResponseEntity(HttpStatus.OK);
    }
}

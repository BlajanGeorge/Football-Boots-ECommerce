package com.example.footballbootswebapiis.controller;

import com.example.footballbootswebapiis.dto.RatingDto;
import com.example.footballbootswebapiis.mappers.RatingMapper;
import com.example.footballbootswebapiis.repository.UserRepository;
import com.example.footballbootswebapiis.service.RatingServiceApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rating")
@Slf4j
@CrossOrigin
public class RatingController {

    private final RatingServiceApi ratingService;
    private final UserRepository userRepository;

    public RatingController(final RatingServiceApi ratingService, UserRepository userRepository) {
        this.ratingService = ratingService;
        this.userRepository = userRepository;
    }

    @GetMapping("/user/{userId}/boots/{bootsId}")
    public ResponseEntity<RatingDto> getRatingByUserAndBootsId(@PathVariable(value = "userId") int userId, @PathVariable(value = "bootsId") int bootsId) {
        log.info("Get rating request received for boots {} and user {}.", bootsId, userId);
        return new ResponseEntity<>(RatingMapper.mapFromModelToDto(ratingService.getRatingByUserAndBootsId(userId, bootsId)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> createRating(@RequestBody RatingDto ratingDto) {
        log.info("Create rating request received for user {} and boots {} with rating {}.", ratingDto.getUserId(), ratingDto.getBootsId(), ratingDto.getRating());
        ratingService.createRating(RatingMapper.mapFromDtoToModel(ratingDto, userRepository.findById(ratingDto.getUserId()).get()));
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Object> updateRating(@RequestBody RatingDto ratingDto) {
        log.info("Update rating request received for user {} and boots {} with rating {}.", ratingDto.getUserId(), ratingDto.getBootsId(), ratingDto.getRating());
        ratingService.updateRating(RatingMapper.mapFromDtoToModel(ratingDto, userRepository.findById(ratingDto.getUserId()).get()));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/boots/{bootsId}/average")
    public ResponseEntity<String> getAverageForBootsId(@PathVariable(value = "bootsId") int bootsId) {
        log.info("Average request received for boots {}.", bootsId);
        return new ResponseEntity<>(ratingService.getAvgForBoots(bootsId).toPlainString(), HttpStatus.OK);
    }
}

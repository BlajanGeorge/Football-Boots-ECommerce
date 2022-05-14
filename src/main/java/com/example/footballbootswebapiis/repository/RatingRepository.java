package com.example.footballbootswebapiis.repository;

import com.example.footballbootswebapiis.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Integer> {

    Rating findByBootsIdAndUserId(int bootsId, int userId);

    List<Rating> findAllByBootsId(int bootsId);
}

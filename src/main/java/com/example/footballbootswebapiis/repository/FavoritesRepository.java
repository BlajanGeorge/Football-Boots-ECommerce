package com.example.footballbootswebapiis.repository;

import com.example.footballbootswebapiis.model.Favorites;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoritesRepository extends JpaRepository<Favorites, Integer> {

    Optional<Favorites> getByUserIdAndBootsId(int userId, int bootsId);

    List<Favorites> findAllByUserId(int userId);

    int countByUserId(int userId);

    void deleteAllByUserId(int userId);

    void deleteAllByBootsId(int bootsId);
}

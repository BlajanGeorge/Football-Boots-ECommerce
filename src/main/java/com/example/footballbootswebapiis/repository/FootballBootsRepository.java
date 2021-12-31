package com.example.footballbootswebapiis.repository;

import com.example.footballbootswebapiis.model.FootballBoots;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FootballBootsRepository extends JpaRepository<FootballBoots, Integer>, JpaSpecificationExecutor<FootballBoots> {
}

package com.example.footballbootswebapiis.repository;

import com.example.footballbootswebapiis.model.FootballBootsAttributes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FootballBootsAttributesRepository extends JpaRepository<FootballBootsAttributes, Integer>, JpaSpecificationExecutor<FootballBootsAttributes> {
}

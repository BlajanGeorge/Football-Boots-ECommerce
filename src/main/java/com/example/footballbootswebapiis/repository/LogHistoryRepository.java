package com.example.footballbootswebapiis.repository;

import com.example.footballbootswebapiis.model.LogHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LogHistoryRepository extends JpaRepository<LogHistory, Integer> {

    @Query(value = "SELECT log FROM LogHistory log WHERE log.timestamp = (SELECT MAX(log.timestamp) FROM LogHistory log WHERE log.user = :user)")
    LogHistory getLogHistoryByUserAndMaxTimestamp(@Param("user") String user);
}

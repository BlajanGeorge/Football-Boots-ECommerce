package com.example.footballbootswebapiis.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "logs")
@Data
@NoArgsConstructor
public class LogHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String user;
    @Column
    private String operation;
    @Column
    private Timestamp timestamp;

    public LogHistory(String user, String operation) {
        this.user = user;
        this.operation = operation;
        this.timestamp = Timestamp.from(Instant.now());
    }
}

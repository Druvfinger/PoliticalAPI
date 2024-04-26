package com.example.politico.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "subjects")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String source;
    @ManyToOne
    @JoinColumn(name = "partyId")
    private PoliticalParty politicalParty;
    private Timestamp lastUpdated;

    public Subject(String name, String source, PoliticalParty politicalParty, Timestamp lastUpdated) {
        this.name = name;
        this.source = source;
        this.politicalParty = politicalParty;
        this.lastUpdated = lastUpdated;
    }
}

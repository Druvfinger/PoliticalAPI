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
@Table(name = "bulletpoints")
public class BulletPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "partyId")
    private PoliticalParty politicalParty;
    private String bulletPoint;
    @ManyToOne
    @JoinColumn(name = "subjectId")
    private Subject subject;
    private Timestamp lastUpdated;

    public BulletPoint(PoliticalParty politicalParty, String bulletPoint, Subject subject, Timestamp lastUpdated) {
        this.politicalParty = politicalParty;
        this.bulletPoint = bulletPoint;
        this.subject = subject;
        this.lastUpdated = lastUpdated;
    }

}

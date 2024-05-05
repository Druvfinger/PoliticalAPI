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
    @JoinColumn(name = "pid")
    private PoliticalParty politicalParty;
    private String bp;
    @ManyToOne
    @JoinColumn(name = "sid")
    private Subject subject;
    @Column(name = "updated", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp updated;

    public BulletPoint(PoliticalParty politicalParty, String bp, Subject subject, Timestamp updated) {
        this.politicalParty = politicalParty;
        this.bp = bp;
        this.subject = subject;
        this.updated = updated;
    }

}

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
@Table(name = "bpchangelog")
public class BpLogEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "type")
    private ChangeTypeEntry entry;
    private String descr;
    @ManyToOne
    @JoinColumn(name = "bpid")
    private BulletPoint bulletPoint;
    private Timestamp updated;

    public BpLogEntry(ChangeTypeEntry entry, String descr, BulletPoint bulletPoint,Timestamp updated) {
        this.entry = entry;
        this.descr = descr;
        this.bulletPoint = bulletPoint;
        this.updated = updated;
    }
}

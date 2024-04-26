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
    @JoinColumn(name = "changeTypeId")
    private ChangeTypeEntry entry;
    private String descr;
    @ManyToOne
    @JoinColumn(name = "bpId")
    private BulletPoint bulletPoint;
    private Timestamp timestamp;

    public BpLogEntry(ChangeTypeEntry entry, String descr, BulletPoint bulletPoint,Timestamp timestamp) {
        this.entry = entry;
        this.descr = descr;
        this.bulletPoint = bulletPoint;
        this.timestamp = timestamp;
    }
}

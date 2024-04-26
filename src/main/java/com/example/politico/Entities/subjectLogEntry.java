package com.example.politico.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class subjectLogEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "Id")
    private changeTypeEntry entry;
    private String descr;
    @ManyToOne
    @JoinColumn(name = "id")
    private BulletPoint bulletPoint;

    public subjectLogEntry(changeTypeEntry entry, String descr, BulletPoint bulletPoint) {
        this.entry = entry;
        this.descr = descr;
        this.bulletPoint = bulletPoint;
    }
}

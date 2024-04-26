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
public class SubjectLogEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "Id")
    private ChangeTypeEntry entry;
    private String descr;
    @ManyToOne
    @JoinColumn(name = "id")
    private Subject subject;
    private Timestamp timestamp;

    public SubjectLogEntry(ChangeTypeEntry entry, String descr, Subject subject, Timestamp timestamp) {
        this.entry = entry;
        this.descr = descr;
        this.subject = subject;
        this.timestamp = timestamp;
    }
}

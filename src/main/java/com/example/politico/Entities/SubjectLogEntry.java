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
@Table(name = "subjectchangelog")
public class SubjectLogEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "type")
    private ChangeTypeEntry entry;
    private String descr;
    @ManyToOne
    @JoinColumn(name = "sid")
    private Subject subject;
    private Timestamp updated;

    public SubjectLogEntry(ChangeTypeEntry entry, String descr, Subject subject, Timestamp updated) {
        this.entry = entry;
        this.descr = descr;
        this.subject = subject;
        this.updated = updated;
    }
}

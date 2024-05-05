package com.example.politico.Repos;

import com.example.politico.Entities.PoliticalParty;
import com.example.politico.Entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface SubjectRepo extends JpaRepository<Subject, Long> {
    List<Subject> findByParty(PoliticalParty party);
    Subject findByNameAndParty(String name, PoliticalParty party);
}

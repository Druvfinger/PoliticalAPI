package com.example.politico.Repos;


import com.example.politico.Entities.SubjectLogEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectLogRepo extends JpaRepository<SubjectLogEntry, Long> {
}

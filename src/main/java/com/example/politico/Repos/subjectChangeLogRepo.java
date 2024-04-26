package com.example.politico.Repos;


import com.example.politico.Entities.subjectLogEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface subjectChangeLogRepo extends JpaRepository<subjectLogEntry, Long> {
}

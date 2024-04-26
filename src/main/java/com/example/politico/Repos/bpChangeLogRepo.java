package com.example.politico.Repos;

import com.example.politico.Entities.bpLogEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface bpChangeLogRepo extends JpaRepository<bpLogEntry, Long> {
}

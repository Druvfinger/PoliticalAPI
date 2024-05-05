package com.example.politico.Repos;

import com.example.politico.Entities.BpLogEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BpLogRepo extends JpaRepository<BpLogEntry, Long> {
}

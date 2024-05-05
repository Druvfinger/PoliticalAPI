package com.example.politico.Repos;

import com.example.politico.Entities.ChangeTypeEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChangeTypeRepo extends JpaRepository<ChangeTypeEntry, Long> {
}

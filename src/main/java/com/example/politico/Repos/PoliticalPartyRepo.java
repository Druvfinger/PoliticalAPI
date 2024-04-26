package com.example.politico.Repos;

import com.example.politico.Entities.PoliticalParty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoliticalPartyRepo extends JpaRepository<PoliticalParty, Long> {
    PoliticalParty findByAbbreviation(String abbreviation);
}

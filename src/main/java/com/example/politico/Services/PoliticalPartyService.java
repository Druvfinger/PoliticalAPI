package com.example.politico.Services;

import com.example.politico.Entities.PoliticalParty;
import com.example.politico.Repos.PoliticalPartyRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PoliticalPartyService {
    private final PoliticalPartyRepo partyRepo;

    public PoliticalPartyService(PoliticalPartyRepo repo){ this.partyRepo = repo; }

    public List<PoliticalParty> getAllParties(){ return partyRepo.findAll(); }

    public PoliticalParty getPartyById(long Id) { return partyRepo.findById(Id).orElse(null); }

}

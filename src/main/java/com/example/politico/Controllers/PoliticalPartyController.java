package com.example.politico.Controllers;

import com.example.politico.Entities.PoliticalParty;
import com.example.politico.Services.PoliticalPartyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("political-parties")
public class PoliticalPartyController {

    private final PoliticalPartyService partyService;

    public PoliticalPartyController (PoliticalPartyService partyService){ this.partyService = partyService; }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<PoliticalParty> getParties() { return partyService.getAllParties(); }

    @GetMapping("/{id}")
    public PoliticalParty getPartyById(@PathVariable long id) { return partyService.getPartyById(id); }


}

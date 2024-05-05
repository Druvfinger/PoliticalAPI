package com.example.politico.Controllers;

import com.example.politico.Entities.PoliticalParty;
import com.example.politico.Services.PoliticalPartyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/political-parties")
public class PoliticalPartyController {

    private final PoliticalPartyService partyService;

    public PoliticalPartyController (PoliticalPartyService partyService){ this.partyService = partyService;
    }

    @GetMapping("/all")
    @Operation(
            tags = {"PoliticalParty"},
            operationId = "getAllParties",
            description = "get All Political Parties",
            responses = {
                    @ApiResponse(responseCode = "200",
                    content = @Content(schema = @Schema(implementation = PoliticalParty.class), mediaType = MediaType.APPLICATION_JSON_VALUE), description = "Successful Response")
            }
    )
    public List<PoliticalParty> getParties() {
        return partyService.getAllParties();
    }

    @GetMapping("/{id}")
    @Operation(
            tags = {"PoliticalParty"},
            operationId = "getPartyById",
            description = "get Political Party by it's Id",
            responses = {
                    @ApiResponse(responseCode = "200",
                    content = @Content(schema = @Schema(implementation = PoliticalParty.class), mediaType = MediaType.APPLICATION_JSON_VALUE), description = "Successful Response")
            }
    )
    public PoliticalParty getPartyById(@PathVariable long id) {
        return partyService.getPartyById(id);
    }

    @GetMapping("/abbreviation")
    @Operation(
            tags = {"PoliticalParty"},
            operationId = "getPartyByAbbreviation",
            description = "get Political Party By Party Abbreviation",
            parameters = {@Parameter(name = "partyAbbreviation", required = true, description = "Party abbreviation ex. ('S')")},
            responses = {
                    @ApiResponse(responseCode = "200",
                    content = @Content(schema = @Schema(implementation = PoliticalParty.class), mediaType = MediaType.APPLICATION_JSON_VALUE), description = "Successful Response")
            }
    )
    public PoliticalParty getPartyByAbbreviation(@RequestParam String abbreviation){
        return partyService.getPartyByAbbreviation(abbreviation);
    }

}

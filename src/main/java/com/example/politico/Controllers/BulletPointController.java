package com.example.politico.Controllers;

import com.example.politico.Entities.BulletPoint;
import com.example.politico.Entities.PoliticalParty;
import com.example.politico.Entities.Subject;
import com.example.politico.Services.BulletPointService;
import com.example.politico.Services.PoliticalPartyService;
import com.example.politico.Services.SubjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/bulletpoints")
public class BulletPointController {

    private final BulletPointService bpService;
    private final PoliticalPartyService pService;
    private final SubjectService sService;

    public BulletPointController(BulletPointService bpService, PoliticalPartyService pService, SubjectService sService) {
        this.bpService = bpService;
        this.pService = pService;
        this.sService = sService;
    }

    @GetMapping("/{id}")
    @Operation(
            tags = {"BulletPoint"},
            operationId = "getBpById",
            description = "get a bulletPoint based on it's Id",
            responses = {
                    @ApiResponse(responseCode = "200",
                    content = @Content(schema = @Schema(implementation = BulletPoint.class), mediaType = MediaType.APPLICATION_JSON_VALUE), description = "Successful Response")
            }
    )
    public BulletPoint getBulletPointById(@PathVariable long id) {
        return bpService.getBulletPointById(id);
    }

    @GetMapping("/all")
    @Operation(
            tags = {"BulletPoint"},
            operationId = "getAllBps",
            description = "Get all bulletPoints",
            responses = {
                    @ApiResponse(responseCode = "200",
                    content = @Content(schema = @Schema(implementation = BulletPoint.class), mediaType = MediaType.APPLICATION_JSON_VALUE), description = "Successful Response")
            }
    )
    public List<BulletPoint> getAllBulletPoints() {
        return bpService.getAllBulletPoints();
    }

    @GetMapping("/party")
    @Operation(
            tags = {"BulletPoint"},
            operationId = "getBpsByParty",
            description = "Get all bulletPoints from specified Party",
            parameters = {
                    @Parameter(name = "partyAbbreviation", required = true, description = "Party abbreviation ex. ('KD')")
            },
            responses = {
                    @ApiResponse(responseCode = "200",
                    content = @Content(schema = @Schema(implementation = BulletPoint.class), mediaType = MediaType.APPLICATION_JSON_VALUE), description = "Successful Response")
            }
    )
    public List<BulletPoint> getBulletPointsByParty(@RequestParam String partyAbbreviation) {
        PoliticalParty party = pService.getPartyByAbbreviation(partyAbbreviation);
        return bpService.getBulletPointsByParty(party);
    }

    @GetMapping("/subject")
    @Operation(
            tags = {"BulletPoint"},
            operationId = "getBpsBySubject",
            description = "Get all bulletPoints from specified Subject (This is party specific Subject)",
            parameters = {@Parameter(name = "subjectId", required = true)},
            responses = {
                    @ApiResponse(responseCode = "200",
                    content = @Content(schema = @Schema(implementation = BulletPoint.class), mediaType = MediaType.APPLICATION_JSON_VALUE), description = "Successful Response")
            }
    )
    public List<BulletPoint> getBulletPointsBySubject(@RequestParam long subjectId) {
        Subject subject = sService.getSubjectById(subjectId);
        return bpService.getBulletPointsBySubject(subject);
    }
}

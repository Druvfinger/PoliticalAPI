package com.example.politico.Controllers;

import com.example.politico.Entities.PoliticalParty;
import com.example.politico.Entities.Subject;
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
@RequestMapping("/subjects")
public class SubjectController {

    private final SubjectService subjectService;
    private final PoliticalPartyService politicalPartyService;

    public SubjectController(SubjectService subjectService, PoliticalPartyService politicalPartyService) {
        this.subjectService = subjectService;
        this.politicalPartyService = politicalPartyService;
    }

    @GetMapping("/all")
    @Operation(
            tags = {"Subject"},
            operationId = "getAllSubjects",
            description = "get all Subjects",
            responses = {
                    @ApiResponse(responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Subject.class), mediaType = MediaType.APPLICATION_JSON_VALUE), description = "Successful Response")
            }
    )
    public List<Subject> getAllSubjects() {
        return subjectService.getAllSubjects();
    }

    @GetMapping("/{id}")
    @Operation(
            tags = {"Subject"},
            operationId = "getSubjectById",
            description = "get Subject by it's Id",
            responses = {
                    @ApiResponse(responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Subject.class), mediaType = MediaType.APPLICATION_JSON_VALUE), description = "Successful Response")
            }
    )
    public Subject getSubjectById(@PathVariable long id) {
        return subjectService.getSubjectById(id);
    }

    @GetMapping("/party")
    @CrossOrigin(origins = "http://127.0.0.1:5500/")
    @Operation(
            tags = {"Subject"},
            operationId = "getSubjectsByParty",
            description = "get all Subjects from a Political Party",
            parameters = {
                    @Parameter(name = "partyAbbreviation", required = true, description = "Party Abbreviation ex.('M')")
            },
            responses = {
                    @ApiResponse(responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Subject.class), mediaType = MediaType.APPLICATION_JSON_VALUE), description = "Successful Response")
            }
    )
    public List<Subject> getSubjectsByParty(@RequestParam String partyAbbreviation) {
        PoliticalParty party = politicalPartyService.getPartyByAbbreviation(partyAbbreviation);
        return subjectService.getSubjectsByParty(party);
    }

    @GetMapping("name-and-party")
    @Operation(
            tags = {"Subject"},
            operationId = "getSubjectsByParty",
            description = "get all Subjects from a Political Party",
            parameters = {
                    @Parameter(name = "subjectName", required = true, description = "SubjectName t.ex. ('Polisen')"),
                    @Parameter(name = "partyAbbreviation", required = true, description = "Party Abbreviation ex.('M')"),
            },
            responses = {
                    @ApiResponse(responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Subject.class), mediaType = MediaType.APPLICATION_JSON_VALUE), description = "Successful Response")
            }
    )
    public Subject getSubjectByNameAndAndPoliticalParty(@RequestParam String subjectName, @RequestParam String partyAbbreviation){
        PoliticalParty party = politicalPartyService.getPartyByAbbreviation(partyAbbreviation);
        return subjectService.getSubjectByNameAndAndPoliticalParty(subjectName, party);
    }
}

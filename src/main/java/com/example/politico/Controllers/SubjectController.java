package com.example.politico.Controllers;

import com.example.politico.Entities.PoliticalParty;
import com.example.politico.Entities.Subject;
import com.example.politico.Services.SubjectService;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping("/all")
    public List<Subject> getAllSubjects() {
        return subjectService.getAllSubjects();
    }

    @GetMapping("/lastUpdated")
    public List<Subject> getSubjectsByLastUpdated(@RequestParam Timestamp timestamp) {
        return subjectService.getSubjectsByLastUpdated(timestamp);
    }

    @GetMapping("/{id}")
    public Subject getSubjectById(@PathVariable long id) {
        return subjectService.getSubjectById(id);
    }

    @GetMapping("/party")
    public List<Subject> getSubjectsByParty(@RequestBody PoliticalParty party) {
        return subjectService.getSubjectsByParty(party);
    }

    @GetMapping("name-and-party")
    public Subject getSubjectByNameAndAndPoliticalParty(@RequestParam String name,@RequestBody PoliticalParty party){
        return subjectService.getSubjectByNameAndAndPoliticalParty(name,party);
    }
}

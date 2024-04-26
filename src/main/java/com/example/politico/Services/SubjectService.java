package com.example.politico.Services;

import com.example.politico.Entities.PoliticalParty;
import com.example.politico.Entities.Subject;
import com.example.politico.Repos.SubjectRepo;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.List;

@Service
public class SubjectService {

    private final SubjectRepo subjectRepo;

    public SubjectService(SubjectRepo subjectRepo) {
        this.subjectRepo = subjectRepo;
    }

    public List<Subject> getAllSubjects () {
        return subjectRepo.findAll();
    }

    public List<Subject> getSubjectsByParty(PoliticalParty party){
        return subjectRepo.findByPoliticalParty(party);
    }

    public Subject getSubjectById(long id){
        return subjectRepo.findById(id).orElse(null);
    }

    public List<Subject> getSubjectsByLastUpdated(Timestamp timestamp){
        return subjectRepo.findByLastUpdated(timestamp);
    }
    public Subject getSubjectByNameAndAndPoliticalParty(String name, PoliticalParty party){
        return subjectRepo.getSubjectByNameAndAndPoliticalParty(name,party);
    }

    public void addSubject(Subject subject) {
        subjectRepo.save(subject);
    }
}

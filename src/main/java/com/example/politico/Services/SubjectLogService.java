package com.example.politico.Services;

import com.example.politico.Entities.SubjectLogEntry;
import com.example.politico.Repos.SubjectLogRepo;
import org.springframework.stereotype.Service;

@Service
public class SubjectLogService {

    private final SubjectLogRepo repo;

    public SubjectLogService(SubjectLogRepo repo) {
        this.repo = repo;
    }

    public void addEntry(SubjectLogEntry entry){
        repo.save(entry);
    }
}

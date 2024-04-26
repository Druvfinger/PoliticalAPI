package com.example.politico.Services;

import com.example.politico.Entities.BpLogEntry;
import com.example.politico.Repos.BpLogRepo;
import org.springframework.stereotype.Service;

@Service
public class BpLogService{

    private final BpLogRepo repo;

    public BpLogService(BpLogRepo repo) {
        this.repo = repo;
    }

    public void addLogEntry(BpLogEntry entry) {
        repo.save(entry);
    }
}

package com.example.politico.Services;

import com.example.politico.Entities.ChangeTypeEntry;
import com.example.politico.Repos.ChangeTypeRepo;
import org.springframework.stereotype.Service;

@Service
public class ChangeTypeService {

    private final ChangeTypeRepo repo;

    public ChangeTypeService(ChangeTypeRepo repo) {
        this.repo = repo;
    }

    public void addEntry(ChangeTypeEntry entry) {
        repo.save(entry);
    }
}

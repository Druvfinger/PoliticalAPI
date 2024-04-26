package com.example.politico.Services;

import com.example.politico.Entities.BulletPoint;
import com.example.politico.Entities.PoliticalParty;
import com.example.politico.Entities.Subject;
import com.example.politico.Repos.BulletPointRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.List;


@Service
public class BulletPointService {

    private final BulletPointRepo bpRepo;

    public BulletPointService(BulletPointRepo bpRepo) { this.bpRepo = bpRepo; }

    public List<BulletPoint> getAllBulletPoints() {
        return bpRepo.findAll();
    }

    public BulletPoint getBulletPointById(long id) {
        return bpRepo.findById(id).orElse(null);
    }

    public List<BulletPoint> getBulletPointsBySubject(Subject subject) {
        return bpRepo.findBySubject(subject);
    }

    public List<BulletPoint> getBulletPointsByParty(PoliticalParty party) {
        return bpRepo.findByPoliticalParty(party);
    }

    public List<BulletPoint> getBulletPointsByLastUpdated(Timestamp timestamp) {
        return bpRepo.findByLastUpdated(timestamp)  ;
    }
    public void addBP(BulletPoint bp){
        bpRepo.save(bp);
    }

}

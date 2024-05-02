package com.example.politico.Repos;

import com.example.politico.Entities.BulletPoint;
import com.example.politico.Entities.PoliticalParty;
import com.example.politico.Entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface BulletPointRepo extends JpaRepository<BulletPoint, Long> {
    List<BulletPoint> findBySubject(Subject subject);
    List<BulletPoint> findByPoliticalParty(PoliticalParty party);
    List<BulletPoint> findByUpdated(Timestamp timestamp);
}

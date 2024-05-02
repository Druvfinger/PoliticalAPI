package com.example.politico.Controllers;

import com.example.politico.Entities.BulletPoint;
import com.example.politico.Entities.PoliticalParty;
import com.example.politico.Entities.Subject;
import com.example.politico.Services.BulletPointService;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/bulletpoints")
public class BulletPointController {

    private final BulletPointService bpService;

    public BulletPointController(BulletPointService bpService) { this.bpService = bpService; }

    @GetMapping("/{id}")
    public BulletPoint getBulletPointById(@PathVariable long id) {
        return bpService.getBulletPointById(id);
    }

    @GetMapping("/all")
    public List<BulletPoint> getAllBulletPoints() {
        return bpService.getAllBulletPoints();
    }

    @GetMapping("/party")
    public List<BulletPoint> getBulletPointsByParty(@RequestBody PoliticalParty party) {
        return bpService.getBulletPointsByParty(party);
    }

    @GetMapping("/subject")
    public List<BulletPoint> getBulletPointsBySubject(@RequestBody Subject subject) {
        return bpService.getBulletPointsBySubject(subject);
    }

    @GetMapping("/lastUpdated")
    public List<BulletPoint> getBulletPointsByLastUpdated(@RequestParam Timestamp timestamp) {
        return bpService.getBulletPointsByLastUpdated(timestamp);
    }
}

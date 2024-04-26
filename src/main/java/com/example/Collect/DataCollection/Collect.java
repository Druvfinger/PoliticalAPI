package com.example.Collect.DataCollection;

import com.example.Collect.Parties.PoliticalPartiesHelper;
import com.example.Collect.Parties.SverigeDemokraterna.SverigeDemokraterna;
import com.example.politico.Entities.*;
import com.example.politico.Services.*;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.sql.*;
import java.util.*;

public class Collect {

    private final SubjectService subjectService;
    private final PoliticalPartyService partyService;
    private final BulletPointService bpService;
    private final SubjectLogService subjectLogService;
    private final BpLogService bpLogService;
    private final ChangeTypeService changeTypeService;
    private final PoliticalPartiesHelper helper;
    public Collect(SubjectService subjectService, PoliticalPartyService partyService, BulletPointService bpService, SubjectLogService subjectLogService, BpLogService bpLogService, ChangeTypeService changeTypeService, PoliticalPartiesHelper helper) {
        this.subjectService = subjectService;
        this.partyService = partyService;
        this.bpService = bpService;
        this.subjectLogService = subjectLogService;
        this.bpLogService = bpLogService;
        this.changeTypeService = changeTypeService;
        this.helper = helper;
    }

    public void fetchAndCommitAllPartiesBPs() throws IOException {
        List<PoliticalParty> parties = partyService.getAllParties();
        for (PoliticalParty party : parties){
            String abbreviation = party.getAbbreviation();
            Map<String, List<String>> bpMap = helper.getAllBulletPointsForParty(abbreviation,helper.getSubjectLinkMap(abbreviation));
            for (Map.Entry<String,List<String>> entry : bpMap.entrySet()){
                Subject subject = subjectService.getSubjectByNameAndAndPoliticalParty(entry.getKey(), party);
                for (String bp : entry.getValue()){
                    BulletPoint bulletPoint = new BulletPoint(party,bp,subject,new Timestamp(System.currentTimeMillis()));
                    BpLogEntry logEntry = new BpLogEntry(changeTypeService.getEntryById(1),"New BP Added", bulletPoint, bulletPoint.getLastUpdated());
                    bpService.addBP(bulletPoint);
                    bpLogService.addLogEntry(logEntry);
                }
            }
        }
    }
    public void fetchAndCommitAllSubjects() throws Exception {
        List<PoliticalParty> parties = partyService.getAllParties();
        String linkSD = "https://sd.se/wp-content/uploads/2022/07/sverigedemokraternas-valplattform-2022-april.pdf";
        for (PoliticalParty party : parties){
            String abbreviation = party.getAbbreviation();
            Map<String, String> subjectLinkMap = new HashMap<>();
            if ("SD".equals(abbreviation)) {
                for (String subject: new SverigeDemokraterna().fetchAllSubjects()){ subjectLinkMap.put(subject, linkSD); }
            }
            else subjectLinkMap = helper.getSubjectLinkMap(abbreviation);
            for (Map.Entry<String,String> entry : subjectLinkMap.entrySet()){
                String source = switch (abbreviation){
                    case "C" -> "https://www.centerpartiet.se" + entry.getValue();
                    case "KD" -> "https://kristdemokraterna.se" + entry.getValue();
                    case "S" -> "https://www.socialdemokraterna.se" + entry.getValue();
                    case "V" -> "https://www.vansterpartiet.se" + entry.getValue();
                    default -> entry.getValue();
                };
                Subject subject = new Subject(entry.getKey(), source, party, new Timestamp(System.currentTimeMillis()));
                SubjectLogEntry logEntry = new SubjectLogEntry(changeTypeService.getEntryById(1),"New Subject Added", subject, subject.getLastUpdated());
                subjectService.addSubject(subject);
                subjectLogService.addEntry(logEntry);
            }
        }
    }

    public int getPartyId(String abbreviation){
        return (int) partyService.getPartyByAbbreviation(abbreviation).getId();
    }
    public int getSubjectId(String name, PoliticalParty party){
        return (int) subjectService.getSubjectByNameAndAndPoliticalParty(name,party).getId();
    }
}

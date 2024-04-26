package com.example.Collect.DataCollection;

import com.example.Collect.Parties.PoliticalPartiesHelper;
import com.example.Collect.Parties.SverigeDemokraterna.SverigeDemokraterna;
import com.example.politico.Entities.PoliticalParty;
import com.example.politico.Entities.Subject;
import com.example.politico.Services.BulletPointService;
import com.example.politico.Services.PoliticalPartyService;
import com.example.politico.Services.SubjectService;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.sql.*;
import java.util.*;

public class Collect {

    private final SubjectService subjectService;
    private final PoliticalPartyService partyService;
    private final BulletPointService bpService;
    private final PoliticalPartiesHelper helper;
    Dotenv dotenv = Dotenv.load();
    private final String dbURL = dotenv.get("DB_URL");
    private final String dbUser = dotenv.get("DB_USER");
    private final String dbPass = dotenv.get("DB_PASS");
    public Collect(SubjectService subjectService, PoliticalPartyService partyService, BulletPointService bpService, PoliticalPartiesHelper helper) {
        this.subjectService = subjectService;
        this.partyService = partyService;
        this.bpService = bpService;
        this.helper = helper;
    }

    public void populateDbWithAllPartiesBulletPoints() throws Exception {
        List<String> abbreviations = partyService.getAllAbbreviations(partyService.getAllParties());
        for (String abbreviation : abbreviations){
            int partyId = getPartyId(abbreviation);
            PoliticalParty party = partyService.getPartyById(partyId);
            Map<String, List<String>> bpMap = helper.getAllBulletPointsForParty(abbreviation,helper.getSubjectLinkMap(abbreviation));
            for (Map.Entry<String,List<String>> entry : bpMap.entrySet()){
                int subjectId = getSubjectId(entry.getKey(), party);
                for (String bp : entry.getValue()){

                    try(Connection con = DriverManager.getConnection(dbURL,dbUser,dbPass)){
                        System.out.println("Trying to add BP: " + bp + " for party: " + abbreviation);
                        String sql = "{CALL addBulletPoint(?,?,?,?)}";
                        CallableStatement callableStatement = con.prepareCall(sql);
                        callableStatement.setInt(1, partyId);
                        callableStatement.setString(2, bp);
                        callableStatement.setInt(3, subjectId);
                        callableStatement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
                        callableStatement.execute();
                    }catch (SQLException e){
                        System.out.println(bp);
                        e.printStackTrace();
                    }
                }
            }
        }
    } //TODO: REMOVE?!
    public void fetchAndCommitAllPartiesBPs() throws IOException {
        List<PoliticalParty> parties = partyService.getAllParties();
        for (PoliticalParty party : parties){
            String abbreviation = party.getAbbreviation();
            Map<String, List<String>> bpMap = helper.getAllBulletPointsForParty(abbreviation,helper.getSubjectLinkMap(abbreviation));
            for (Map.Entry<String,List<String>> entry : bpMap.entrySet()){
                Subject subject = subjectService.getSubjectByNameAndAndPoliticalParty(entry.getKey(), party);
                for (String bp : entry.getValue()){
                    bpService.addBulletPoint(bp,subject,party);
                    //TODO: register change to bpChangeLog
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
                subjectService.addSubject(subject);
                //TOD0: register change to subjectChangeLog
            }
        }
    }
    public void populateSubjectsTable() throws Exception {
        List<String> abbreviations = partyService.getAllAbbreviations(partyService.getAllParties());
        for (String abbreviation : abbreviations){
            Map<String, String> subjectLinkMap = new HashMap<>();
            if (abbreviation.equals("SD")){
                for (String subject: new SverigeDemokraterna().fetchAllSubjects()){
                    subjectLinkMap.put(subject, "https://sd.se/wp-content/uploads/2022/07/sverigedemokraternas-valplattform-2022-april.pdf");
                }
            } else {
                subjectLinkMap = helper.getSubjectLinkMap(abbreviation);
            }
            int partyId = getPartyId(abbreviation);
            for (Map.Entry<String,String> entry : subjectLinkMap.entrySet()){
                try(Connection con = DriverManager.getConnection(dbURL,dbUser,dbPass)){
                    System.out.println("Trying to add subject: " + entry.getKey() + " for party: " + abbreviation);
                    String sql = "{CALL addSubject(?,?,?,?)}";
                    String source = switch (abbreviation){
                        case "C" -> "https://www.centerpartiet.se" + entry.getValue();
                        case "KD" -> "https://kristdemokraterna.se" + entry.getValue();
                        case "S" -> "https://www.socialdemokraterna.se" + entry.getValue();
                        case "V" -> "https://www.vansterpartiet.se" + entry.getValue();
                        default -> entry.getValue();
                    };
                    CallableStatement callableStatement = con.prepareCall(sql);
                    callableStatement.setString(1, entry.getKey());
                    callableStatement.setString(2, source);
                    callableStatement.setInt(3, partyId);
                    callableStatement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
                    callableStatement.execute();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
        //TODO: THIS NEEDS SAFETY SO WE DO NOT JUST KEEP ADDING THINGS THAT EXISTS ALREADY
    }

    public int getPartyId(String abbreviation){
        return (int) partyService.getPartyByAbbreviation(abbreviation).getId();
    }
    public int getSubjectId(String name, PoliticalParty party){
        return (int) subjectService.getSubjectByNameAndAndPoliticalParty(name,party).getId();
    }
}

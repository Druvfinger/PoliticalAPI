package com.example.Collect.DataCollection;

import com.example.Collect.Parties.PoliticalPartiesHelper;
import com.example.politico.Repos.SubjectRepo;
import com.example.politico.Services.PoliticalPartyService;
import com.example.politico.Services.SubjectService;

import java.sql.*;
import java.util.*;

public class Collect {

    private final SubjectService subjectService;
    private final PoliticalPartyService partyService;
    private final PoliticalPartiesHelper helper;

    public Collect(SubjectService subjectService, PoliticalPartyService partyService, PoliticalPartiesHelper helper) {
        this.subjectService = subjectService;
        this.partyService = partyService;
        this.helper = helper;
    }

    public void populateDbWithAllPartiesBulletPoints() throws Exception {
        List<String> abbreviations = getPartyAbbreviations();
        for (String abbreviation : abbreviations){
            int partyId = getPartyId(abbreviation);
            Map<String, List<String>> bpMap = helper.getAllBulletPointsForParty(abbreviation,helper.getSubjectLinkMap(abbreviation));
            for (Map.Entry<String,List<String>> entry : bpMap.entrySet()){
                int subjectId = getSubjectId(entry.getKey(), partyId);
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
    }

    public void populateSubjectsTable() throws Exception {
        List<String> abbreviations = getPartyAbbreviations();
        for (String abbreviation : abbreviations){
            Map<String, String> subjectLinkMap = new HashMap<>();
            if (abbreviation.equals("SD")){
                for (String s: new SverigeDemokraterna().getAllSubjects()){
                    subjectLinkMap.put(s, "https://sd.se/wp-content/uploads/2022/07/sverigedemokraternas-valplattform-2022-april.pdf");
                }
            } else {
                subjectLinkMap = helper.getSubjectLinkMap(abbreviation);
            }
            int partyId = getPartyId(abbreviation);
            for (Map.Entry<String,String> entry : subjectLinkMap.entrySet()){
                try(Connection con = DriverManager.getConnection(dbURL,dbUser,dbPass)){
                    System.out.println("Trying to add subject: " + entry.getKey() + " for party: " + abbreviation);
                    String sql = "{CALL addSubject(?,?,?,?)}";
                    String source =switch (abbreviation){
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

    public List<String> getPartyAbbreviations(){
        String sql = "select * from parties";
        List<String> partyAbbreviations = new ArrayList<>();
        try(Connection con = DriverManager.getConnection(dbURL,dbUser,dbPass);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()){
                partyAbbreviations.add(rs.getString("abbreviation"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return partyAbbreviations;
    }
}

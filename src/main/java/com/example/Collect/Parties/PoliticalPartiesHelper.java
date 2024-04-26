package com.example.Collect.Parties;


import com.example.Collect.Parties.CenterPartiet.CenterPartiet;
import com.example.Collect.Parties.KristDemokraterna.KristDemokraterna;
import com.example.Collect.Parties.Liberalerna.Liberalerna;
import com.example.Collect.Parties.MiljoPartiet.MiljoPartiet;
import com.example.Collect.Parties.Moderaterna.Moderaterna;
import com.example.Collect.Parties.SocialDemokraterna.SocialDemokraterna;
import com.example.Collect.Parties.SverigeDemokraterna.SverigeDemokraterna;
import com.example.Collect.Parties.VansterPartiet.VansterPartiet;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PoliticalPartiesHelper {
    public String capitalizeSentence(String sentence) {
        return sentence.replaceFirst(String.valueOf(sentence.charAt(0)), String.valueOf(sentence.charAt(0)).toUpperCase());
    }

    public Elements getElementsWithoutChildren(Elements elements) {
        Elements elementsWithoutChildren = new Elements();
        for (Element element : elements) {
            if (element.childrenSize() > 0) continue;
            elementsWithoutChildren.add(element);
        }
        return elementsWithoutChildren;
    }

    public Map<String, String> getSubjectLinkMap(String partyAbbreviation) throws IOException {
        return switch (partyAbbreviation) {
            case "C" -> new CenterPartiet().getSubjectLinkMap();
            case "KD" -> new KristDemokraterna().getSubjectLinkMap();
            case "L" -> new Liberalerna().getSubjectLinkMap();
            case "MP" -> new MiljoPartiet().getSubjectLinkMap();
            case "M" -> new Moderaterna().getSubjectLinkMap();
            case "S" -> new SocialDemokraterna().getSubjectLinkMap();
            case "V" -> new VansterPartiet().getSubjectLinkMap();
            default -> null;
        };
    }
    public List<String> removeEmptyEntriesInList(List<String> list) {
        List<String> returnList = new ArrayList<>();
        for (String listEntry: list) if (!listEntry.isBlank()) returnList.add(listEntry);
        return returnList;
    }
    public List<String> cleanBulletPoints(List<String> bulletPoints){
        List<String> cleanerBulletPoints = removeEmptyEntriesInList(bulletPoints);
        List<String> cleanedBulletPoints = new ArrayList<>();
        cleanerBulletPoints.forEach(bp -> cleanedBulletPoints.add(capitalizeSentence(bp)));
        return cleanedBulletPoints;
    }
    public Map<String, List<String>> getAllBulletPointsForParty(String partyAbbreviation, Map<String, String> subjectLinkMap) throws IOException {
        Map<String, List<String>> bulletPointsMap = new HashMap<>();
        if (partyAbbreviation.equals("SD")) {
            SverigeDemokraterna SD = new SverigeDemokraterna();
            List<String> subjects = SD.getAllSubjects();
            for (String subject : subjects) bulletPointsMap.put(subject, SD.getBulletPoints(subject));
        } else if (null != subjectLinkMap) {
            CenterPartiet C = new CenterPartiet();
            KristDemokraterna KD = new KristDemokraterna();
            Liberalerna L = new Liberalerna();
            MiljoPartiet MP = new MiljoPartiet();
            Moderaterna M = new Moderaterna();
            SocialDemokraterna S = new SocialDemokraterna();
            VansterPartiet V = new VansterPartiet();
            for (Map.Entry<String, String> entry : subjectLinkMap.entrySet()) {
                switch (partyAbbreviation) {
                    case "C" -> bulletPointsMap.put(entry.getKey(), C.getBulletPoints(entry.getValue()));
                    case "KD" -> bulletPointsMap.put(entry.getKey(), KD.getBulletPoints(entry.getValue()));
                    case "L" -> bulletPointsMap.put(entry.getKey(), L.getBulletPoints(entry.getValue()));
                    case "MP" -> bulletPointsMap.put(entry.getKey(), MP.getBulletPoints(entry.getValue()));
                    case "M" -> bulletPointsMap.put(entry.getKey(), M.getBulletPoints(entry.getValue()));
                    case "S" -> bulletPointsMap.put(entry.getKey(), S.getBulletPoints(entry.getValue()));
                    case "V" -> bulletPointsMap.put(entry.getKey(), V.getBulletPoints(entry.getValue()));
                }
            }
        } else throw new Error("Unexpected Issue");
        return bulletPointsMap;
    }

    public void printAllBulletPointsForParty(Map<String, List<String>> bulletPointsMap) {
        for (Map.Entry<String, List<String>> entry : bulletPointsMap.entrySet()) {
            System.out.println(entry.getKey());
            entry.getValue().forEach(System.out::println);
            System.out.println("-------------------------");
        }
    }
}
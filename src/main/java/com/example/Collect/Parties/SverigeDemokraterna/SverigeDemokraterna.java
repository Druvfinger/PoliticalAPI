package com.example.Collect.Parties.SverigeDemokraterna;

import com.example.Collect.Parties.PoliticalPartiesHelper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SverigeDemokraterna {
    String path = "C:\\Users\\Richard\\Documents\\GitHub\\politicalDataTest\\src\\main\\resources\\SD.txt";
    public List<String> getBulletPoints (String subject) throws IOException {
        PoliticalPartiesHelper helper = new PoliticalPartiesHelper();
        Scanner scanner = new Scanner(new File(path));
        List<String> bulletPoints = new ArrayList<>();
        boolean isCorrectSubject = false;
        while (scanner.hasNext()){
            String line = scanner.nextLine();
            if (line.equals(subject)) isCorrectSubject = true;
            if (isCorrectSubject && !line.isBlank()) bulletPoints.add(line);
            if (line.isBlank() && isCorrectSubject) break;
        }
        bulletPoints = bulletPoints.subList(1, bulletPoints.size());
        return helper.cleanBulletPoints(bulletPoints);
    }
    public List<String> fetchAllSubjects() throws IOException{
        Scanner scanner = new Scanner(new File(path));
        List<String> subjects = new ArrayList<>();
        while (scanner.hasNext()){
            String line = scanner.nextLine();
            if (line.isBlank()) subjects.add(scanner.nextLine());
        }
        return subjects;
    }
}

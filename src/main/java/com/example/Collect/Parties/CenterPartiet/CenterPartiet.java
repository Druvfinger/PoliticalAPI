package com.example.Collect.Parties.CenterPartiet;


import com.example.Collect.Parties.PoliticalPartiesHelper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CenterPartiet {

    public List<String> getBulletPoints(String endOfUrl) throws IOException {
        PoliticalPartiesHelper helper = new PoliticalPartiesHelper();
        List<String> bulletPoints = new ArrayList<>();
        final String url = "https://www.centerpartiet.se" + endOfUrl;
        Document doc = Jsoup.parse(new URL(url).openStream(), StandardCharsets.UTF_8.name(), url);
        Elements elements = doc.getElementsByClass("sv-text-portlet-content");
        elements.forEach(e -> e.getElementsByTag("li").forEach(liE -> bulletPoints.add(liE.text().trim())));
        return helper.cleanBulletPoints(bulletPoints);
    }
    public Map<String, String> getSubjectLinkMap() throws IOException{
        Map<String, String> subjectLinkMap = new HashMap<>();
        final String url = "https://www.centerpartiet.se/var-politik/politik-a-o/";
        Document doc = Jsoup.parse(new URL(url).openStream(), StandardCharsets.UTF_8.name(), url);
        Elements elements = doc.getElementsByClass("sv-layout sol-js-collapse-content");
        Elements subjectElements = new Elements();
        for (Element element : elements) subjectElements.addAll(element.getElementsByTag("a"));
        for (Element subjectElement : subjectElements){
            String subject = subjectElement.text().startsWith("För ")
                    ? "EU - " + subjectElement.text()
                    : subjectElement.text();
            String link = subjectElement.attr("href");
            link = link.startsWith("javascript") ? link.split("'")[1].trim() : link;
            subjectLinkMap.put(subject, link);
        }
        return subjectLinkMap;
    }
}

package com.example.Collect.Parties.Liberalerna;

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

public class Liberalerna {

    public List<String> getBulletPoints(String url) throws IOException {
        PoliticalPartiesHelper helper = new PoliticalPartiesHelper();
        Document doc = Jsoup.parse(new URL(url).openStream(), StandardCharsets.UTF_8.name(), url);
        Elements elements = doc.getElementsByClass("wysiwyg-content");
        Elements pTags = elements.get(1).getElementsByTag("p");
        List<String> bulletPoints = new ArrayList<>();
        pTags.forEach(pTag -> bulletPoints.add(pTag.text()));
        return helper.cleanBulletPoints(bulletPoints);
    }
    public Map<String,String> getSubjectLinkMap() throws IOException{
        Map<String, String> subjectLinkMap = new HashMap<>();
        final String url = "https://www.liberalerna.se/politik/";
        Document doc = Jsoup.parse(new URL(url).openStream(), StandardCharsets.UTF_8.name(), url);
        Elements elements = doc.getElementsByClass("politicsIdx-list");
        for(Element element: elements){
            Elements aElements = element.getElementsByTag("a");
            for (Element aElement : aElements){
                subjectLinkMap.put(aElement.text().trim(), aElement.attr("href"));
            }
        }
        return subjectLinkMap;
    }
}

package com.example.Collect.Parties.MiljoPartiet;

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

public class MiljoPartiet {

    public List<String> getBulletPoints(String url) throws IOException {
        PoliticalPartiesHelper helper = new PoliticalPartiesHelper();
        Document doc = Jsoup.parse(new URL(url).openStream(), StandardCharsets.UTF_8.name(), url);
        Elements elements = doc.getElementsByClass("content__container container medium-width").first().getElementsByTag("li");
        Elements finalElements = helper.getElementsWithoutChildren(elements);
        List<String> bulletPoints = new ArrayList<>();
        for (Element element : finalElements){ bulletPoints.add(element.text()); }
        return helper.cleanBulletPoints(bulletPoints);
    }
    public Map<String, String> getSubjectLinkMap () throws IOException{
        Map<String, String> subjectLinkMap = new HashMap<>();
        final String url = "https://www.mp.se/politik";
        Document doc = Jsoup.parse(new URL(url).openStream(), StandardCharsets.UTF_8.name(), url);
        Elements elements = doc.getElementsByClass("question-link");
        for (Element element : elements){
            subjectLinkMap.put(element.text().trim(), element.attr("href"));
        }
        return subjectLinkMap;
    }
}

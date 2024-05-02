package com.example.Collect.Parties.KristDemokraterna;

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

public class KristDemokraterna {

    public List<String> getBulletPoints (String endOfUrl) throws IOException {
        PoliticalPartiesHelper helper = new PoliticalPartiesHelper();
        List<String> bulletPoints = new ArrayList<>();
        List<Element> pTags = new ArrayList<>();
        final String url = "https://kristdemokraterna.se" + endOfUrl;
        Document doc = Jsoup.parse(new URL(url).openStream(), StandardCharsets.UTF_8.name(), url);
        Elements elements = doc.getElementsByClass("sv-text-portlet-content");
        elements.forEach(e -> pTags.addAll(e.getElementsByTag("p")));
        bulletPoints.add(pTags.get(1).text());
        return helper.cleanBulletPoints(bulletPoints);
    }
    public Map<String,String> getSubjectLinkMap() throws IOException{
        Map<String, String> subjectLinkMap = new HashMap<>();
        final String url = "https://kristdemokraterna.se/var-politik/politik-a-till-o/";
        Document doc = Jsoup.parse(new URL(url).openStream(), StandardCharsets.UTF_8.name(), url);
        Elements elements = doc.getElementsByClass("item-108hbpb");
        for (Element element : elements) {
            if (element.tagName().equals("a")) subjectLinkMap.put(element.text(), element.attr("href"));
        }
        return subjectLinkMap;
    }
}

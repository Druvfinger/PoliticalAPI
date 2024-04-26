package com.example.Collect.Parties.SocialDemokraterna;

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

public class SocialDemokraterna {

    public List<String> getBulletPoints(String endOfUrl) throws IOException {
        PoliticalPartiesHelper helper = new PoliticalPartiesHelper();
        final String url = "https://www.socialdemokraterna.se" + endOfUrl;
        Document doc = Jsoup.parse(new URL(url).openStream(), StandardCharsets.UTF_8.name(), url);
        Elements elements = doc.getElementsByClass("sv-text-portlet-content");
        List<String> bulletPoints = new ArrayList<>();
        for (int i = 0; i <= elements.size() -1; i++) {
            if (elements.get(i).text().equals("Socialdemokraterna vill")){
                Elements subElements = elements.get(i + 1).getElementsByTag("li");
                subElements.forEach(e -> bulletPoints.add(e.text().trim()));
                break;
            }
            if (i == elements.size()){
                System.out.println("No bulletPoints found");
            }
        }
        return helper.cleanBulletPoints(bulletPoints);
    }
    public Map<String, String> getSubjectLinkMap() throws IOException{
        Map<String, String> subjectLinkMap = new HashMap<>();
        final String url = "https://www.socialdemokraterna.se/var-politik/a-till-o/";
        Document doc = Jsoup.parse(new URL(url).openStream(), StandardCharsets.UTF_8.name(), url);
        Elements elements = doc.getElementsByClass("sap-ao-lettergroup-topic");
        for (Element element : elements){
            subjectLinkMap.put(element.text().trim(), element.parent().attr("href"));
        }
        return subjectLinkMap;
    }
    //TODO: capitalizeSentence() crashes this why ??
}

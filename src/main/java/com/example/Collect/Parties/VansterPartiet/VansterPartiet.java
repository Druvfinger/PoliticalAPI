package com.example.Collect.Parties.VansterPartiet;

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

public class VansterPartiet {

    public List<String> getBulletPoints(String endOfUrl) throws IOException {
        PoliticalPartiesHelper helper = new PoliticalPartiesHelper();
        final String url = "https://www.vansterpartiet.se" + endOfUrl;
        Document doc = Jsoup.parse(new URL(url).openStream(), StandardCharsets.UTF_8.name(), url);
        List<String> bulletPoints = new ArrayList<>();
        if (!doc.getElementsByTag("ul").isEmpty()){
            List<Element> elementList = new ArrayList<>();
            Elements elements = doc.getElementsByClass("TextContent-module--component--I1Ox9");
            for (Element element : elements){ elementList.addAll(element.getElementsByTag("li")); }
            elementList.forEach(element -> bulletPoints.add(element.text()));
        }
        bulletPoints.addAll(getBulletPointsInTextTag(doc.getElementsByTag("p")));
        return helper.cleanBulletPoints(bulletPoints);
    }
    public Map<String, String> getSubjectLinkMap() throws IOException{
        Map<String, String> subjectLinkMap = new HashMap<>();
        final String url = "https://www.vansterpartiet.se/var-politik/politik-a-o/";
        Document doc = Jsoup.parse(new URL(url).openStream(), StandardCharsets.UTF_8.name(), url);
        Elements elements = doc.getElementsByClass("AlphabeticalPostsModule-module--item--Ow2EX");
        for (Element element : elements){
            subjectLinkMap.put(element.text().trim(), element.child(0).attr("href"));
        }
        return subjectLinkMap;
    }
    private List<String> getBulletPointsInTextTag (Elements pElements){
        List<String> triggerPhrases = List.of("Vi föreslår bland annat:",  "Vänsterpartiet vill bland annat:", "Vi vill", "Vänsterpartiet vill", "Vidare vill vi", "Därför vill","vill vi");
        List<String> bp = new ArrayList<>();
        for (Element pElement : pElements){
            for (String trigger :triggerPhrases){
                if (pElement.text().contains(trigger) && !pElement.text().equals(trigger)){
                    bp.add(pElement.text());
                    break;
                }
            }
        }
        return bp;
    }
    //TODO: There are significant problems with what to include and not when it comes to extracting
    // bullet points from pTags
}

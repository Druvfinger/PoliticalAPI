package com.example.Collect.Parties.Moderaterna;

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

public class Moderaterna {

    public List<String> getBulletPoints(String url) throws IOException {
        PoliticalPartiesHelper helper = new PoliticalPartiesHelper();
        Document doc = Jsoup.parse(new URL(url).openStream(), StandardCharsets.UTF_8.name(), url);
        Elements elements = doc.getAllElements();
        List<Element> elementList = new ArrayList<>();
        List<Element> searchTermElements = new ArrayList<>();
        List<String> bulletPoints = new ArrayList<>();
        searchTermElements.addAll(doc.select("p:contains(Därför vill Moderaterna)"));
        searchTermElements.addAll(doc.select("p:contains(Moderaternas förslag:)"));
        searchTermElements.addAll(doc.select("p:contains(Moderaterna vill därför:)"));
        searchTermElements.addAll(doc.select("p:contains(Moderaterna vill:)"));
        for (int i = 0; i <= elements.size() - 1; i++) {
            if (searchTermElements.contains(elements.get(i))) elementList.add(elements.get(i + 2));
        }
        Elements finalElements = new Elements();
        for (Element element : elementList) finalElements.addAll(element.getElementsByTag("li"));
        for (Element element : finalElements) bulletPoints.add(element.text());
        return helper.cleanBulletPoints(bulletPoints);
    }

    public Map<String, String> getSubjectLinkMap() throws IOException {
        final String url = "https://moderaterna.se/var-politik/";
        Map<String, String> subjectLinkMap = new HashMap<>();
        Document doc = Jsoup.parse(new URL(url).openStream(), StandardCharsets.UTF_8.name(), url);
        Elements elements = doc.getElementsByClass("search-subjects__content--search__form--list__subjects");
        for (Element element : elements) {
            Elements subjectElements = element.getElementsByTag("a");
            for (Element subjectElement : subjectElements) {
                if (subjectElement.text().trim().equalsIgnoreCase("plånbokslöfte")) continue;
                subjectLinkMap.put(subjectElement.text().trim(), subjectElement.attr("href"));
            }
        }
        return subjectLinkMap;
    }
    //TODO: look over "empty" pages that might just have info displayed elswhere
}

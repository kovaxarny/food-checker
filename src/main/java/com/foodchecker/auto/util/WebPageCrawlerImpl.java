package com.foodchecker.auto.util;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class WebPageCrawlerImpl implements WebPageCrawler{
    @Override
    public Document getSite(String urlString) {
        Connection conn = Jsoup.connect(urlString);
        Document document = null;
        try {
            document = conn.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }
}

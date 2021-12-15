package com.foodchecker.auto.util;

import org.jsoup.nodes.Document;

public interface WebPageCrawler {
    Document getSite(String url);
}

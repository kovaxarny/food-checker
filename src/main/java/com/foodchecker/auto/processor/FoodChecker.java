package com.foodchecker.auto.processor;

import com.foodchecker.auto.util.NotificationHandler;
import com.foodchecker.auto.util.WebPageCrawler;
import com.foodchecker.auto.util.WebPageCrawlerImpl;
import com.foodchecker.auto.util.WindowsNotificationHandlerImpl;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FoodChecker {
    public static final String STRING_WINNER_ROW = "winner_row";
    public static final String STRING_ARNOLD = "Arnold";
    public static final String STRING_KOVACS_HUN = "Kovács";
    public static final String STRING_KOVACS_ENG = "Kovacs";

    public void checkPizza() {
        checkWebPage("https://akropolisz-gyros.hu/nyertesek/");
        checkWebPage("https://donpedropizza.hu/nyertesek/");
        checkWebPage("https://corleoneristorante.hu/nyertesek/");
    }

    private static void checkWebPage(String url) {
        WebPageCrawler webPageCrawler = new WebPageCrawlerImpl();
        Document doc = webPageCrawler.getSite(url);

        LocalDate date = LocalDate.now();
        LocalDate yesterDate = date.minusDays(1);
        String dateString = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        String yesterDateString = yesterDate.format(DateTimeFormatter.ISO_LOCAL_DATE);

        for (Element element : doc.getElementsByClass(STRING_WINNER_ROW)) {
            if (element.text().contains(dateString) || element.text().contains(yesterDateString)) {
                System.out.println(url + ": " + element.text());
                if (element.text().contains(STRING_ARNOLD)) {
                    if (element.text().contains(STRING_KOVACS_ENG) || element.text().contains(STRING_KOVACS_HUN)) {
                        NotificationHandler notificationHandler = new WindowsNotificationHandlerImpl();
                        notificationHandler.showNotification("Claim Free " + url);
                    }
                }
            }
        }
    }
}

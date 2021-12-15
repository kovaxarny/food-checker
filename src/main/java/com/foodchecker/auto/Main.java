package com.foodchecker.auto;

import com.foodchecker.auto.processor.FoodChecker;
import com.foodchecker.auto.util.NotificationHandler;
import com.foodchecker.auto.util.WebPageCrawler;
import com.foodchecker.auto.util.WebPageCrawlerImpl;
import com.foodchecker.auto.util.WindowsNotificationHandlerImpl;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main {

    public static final long MILLIS_BEFORE_CONSOLE_CLOSE = 5000L;

    public static void main(String[] args) {
        FoodChecker foodChecker = new FoodChecker();
        foodChecker.checkPizza();
        try {
            Thread.sleep(MILLIS_BEFORE_CONSOLE_CLOSE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}


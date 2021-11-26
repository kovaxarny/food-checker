package com.pizzachecker.auto;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.*;

public class Main {
    public static final String STRING_WINNER_ROW = "winner_row";
    public static final String STRING_ARNOLD = "Arnold";
    public static final String STRING_KOVACS = "Kovács";
    public static final int INT_NUMBER_OF_NOTIFICATIONS = 10;
    public static final long MILLIS_BETWEEN_NOTIFICATIONS = 1000L;

    static Handler fileHandler = null;
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        setup();
        ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
        ses.scheduleAtFixedRate(Main::checkPizza, 0, 1, TimeUnit.HOURS);
    }

    private static void checkPizza() {
        try {
            check("https://akropolisz-gyros.hu/nyertesek/");
            check("https://donpedropizza.hu/nyertesek/");
            check("https://corleoneristorante.hu/nyertesek/");
        } catch (InterruptedException | IOException e) {
            LOGGER.info(e.getMessage());
            Thread.currentThread().interrupt();
        }

    }

    private static void check(String url) throws InterruptedException, IOException {
        LocalDate date = LocalDate.now();
        LocalDate yesterDate = date.minusDays(1);
        String dateString = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        String yesterDateString = yesterDate.format(DateTimeFormatter.ISO_LOCAL_DATE);

        Document doc = getSite(url);   // pretty print HTML
        for (Element element : doc.getElementsByClass(STRING_WINNER_ROW)) {
            if (element.text().contains(dateString) || element.text().contains(yesterDateString)) {
                LOGGER.log(Level.INFO, "{0}", url + ": " + element.text());
                if (element.text().contains(STRING_ARNOLD) && element.text().contains(STRING_KOVACS)) {
                    createNotification("Claim Free " + url);
                }
            }
        }
    }

    public static void createNotification(String message) throws InterruptedException {
        SystemTray tray = SystemTray.getSystemTray();
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        TrayIcon trayIcon = new TrayIcon(image, "Pizza Notification");
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip(message);
        for (int i = 0; i < INT_NUMBER_OF_NOTIFICATIONS; i++) {
            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                LOGGER.info(e.getMessage());
            }
            trayIcon.displayMessage("Free Pizza", message, TrayIcon.MessageType.INFO);
            Thread.sleep(MILLIS_BETWEEN_NOTIFICATIONS);
            tray.remove(trayIcon);
        }
    }


    private static Document getSite(String urlString) throws IOException {
        Connection conn = Jsoup.connect(urlString);
        return conn.get();
    }

    public static void setup() {
        try {
            fileHandler = new FileHandler("./pizza_checker.log");//file
            SimpleFormatter simple = new SimpleFormatter();
            fileHandler.setFormatter(simple);

            LOGGER.addHandler(fileHandler);//adding Handler for file
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
        }
    }
}


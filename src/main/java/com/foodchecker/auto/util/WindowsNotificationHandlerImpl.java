package com.foodchecker.auto.util;

import java.awt.*;

public class WindowsNotificationHandlerImpl implements NotificationHandler {
    public static final int INT_NUMBER_OF_NOTIFICATIONS = 10;
    public static final long MILLIS_BETWEEN_NOTIFICATIONS = 1000L;

    @Override
    public void showNotification(String message){
        SystemTray tray = SystemTray.getSystemTray();
        TrayIcon trayIcon = setUpTrayIcon(message);

        for (int i = 0; i < INT_NUMBER_OF_NOTIFICATIONS; i++) {
            try {
                tray.add(trayIcon);
                trayIcon.displayMessage("Free Pizza", message, TrayIcon.MessageType.INFO);
                Thread.sleep(MILLIS_BETWEEN_NOTIFICATIONS);
                tray.remove(trayIcon);
            } catch (AWTException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private TrayIcon setUpTrayIcon(String message) {
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        TrayIcon trayIcon = new TrayIcon(image, "Pizza Notification");
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip(message);
        return trayIcon;
    }
}

package control.support;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public final class NotificationsMaker {

    private NotificationsMaker() {
    }

    public static void popUpSimpleErrorNotification(String title, String text) {
        Notifications notificationBuilder = Notifications.create()
                .title(title)
                .text(text)
                .hideAfter(Duration.seconds(2))
                .position(Pos.CENTER)
                .onAction((ActionEvent event) -> {
                    System.out.println("Clicked on Notification");
                });
//        notificationBuilder.darkStyle();
        notificationBuilder.showError();
    }

    public static void popUpSimpleInformationNotification(String title, String text) {
        Notifications notificationBuilder = Notifications.create()
                .title(title)
                .text(text)
                .hideAfter(Duration.seconds(2))
                .position(Pos.CENTER)
                .onAction((ActionEvent event) -> {
                    System.out.println("Clicked on Notification");
                });
//        notificationBuilder.darkStyle();
        notificationBuilder.showInformation();
    }

    public static void popUpSimpleWarningNotification(String title, String text) {
        Notifications notificationBuilder = Notifications.create()
                .title(title)
                .text(text)
                .hideAfter(Duration.seconds(2))
                .position(Pos.CENTER)
                .onAction((ActionEvent event) -> {
                    System.out.println("Clicked on Notification");
                });
//        notificationBuilder.darkStyle();
        notificationBuilder.showWarning();
    }

    public static void popUpSimpleWarningNotification(String title, String text, int seconds) {
        Notifications notificationBuilder = Notifications.create()
                .title(title)
                .text(text)
                .hideAfter(Duration.seconds(seconds))
                .position(Pos.CENTER)
                .onAction((ActionEvent event) -> {
                    System.out.println("Clicked on Notification");
                });
//        notificationBuilder.darkStyle();
        notificationBuilder.showWarning();
    }
}

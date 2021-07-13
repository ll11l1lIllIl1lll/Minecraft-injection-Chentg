package cn.snowflake.rose.notification;

import cn.snowflake.rose.mod.Module;

import java.util.ArrayList;

public class NotificationManager {
    private ArrayList<Notification> notifications = new ArrayList<>();

    public void addNotification(String message, int stayTime, Notification.Type type) {
        notifications.add(new Notification(message, stayTime, type));
    }
    public void addNotification(String message, Notification.Type type) {
        notifications.add(new Notification(message, 1000, type));
    }

    public void addNotification(String mod, String message, int stayTime, Notification.Type type) {
        notifications.add(new Notification("\2477[\247b"+mod+"\2477] \2477- \247e" + message, stayTime, type));
    }
    public void addNotification(String mod, String message, Notification.Type type) {
        notifications.add(new Notification("\2477[\247b"+mod+"\2477] \2477- \247e" + message, 2000, type));
    }

    public void addNotification(Module mod, String message, int stayTime, Notification.Type type) {
        notifications.add(new Notification("\2477[\247b"+mod.getName()+"\2477] \2477- \247e" + message, stayTime, type));
    }
    public void addNotification(Module mod, String message, Notification.Type type) {
        notifications.add(new Notification("\2477[\247b"+mod.getName()+"\2477] \2477- \247e" + message, 2000, type));
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }


}

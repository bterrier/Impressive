package net.gehngis.impressive;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

public class MyService extends NotificationListenerService {
    public MyService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        doubleKill = RingtoneManager.getRingtone(getApplicationContext(),
                Uri.parse("android.resource://net.gehngis.impressive/" + R.raw.double_kill));
        tripleKill = RingtoneManager.getRingtone(getApplicationContext(),
                Uri.parse("android.resource://net.gehngis.impressive/" + R.raw.triple_kill));
        overkill = RingtoneManager.getRingtone(getApplicationContext(),
                Uri.parse("android.resource://net.gehngis.impressive/" + R.raw.overkill));
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        Log.d("Service", "New notification");
        super.onNotificationPosted(sbn);

        if (sbn.isOngoing())
            return;

        StatusBarNotification[] notifications = getActiveNotifications();
        Log.d("Service", String.format("Notification count = %d", notifications.length));
        int count = 0;
        for (StatusBarNotification n : notifications) {
            if (n.isOngoing())
                continue;
            int number = n.getNotification().number;
            if (number == 0)
                count += 1;
            else
                count += number;
        }
        Log.d("Service", String.format("count = %d", count));
        Ringtone rt = getRingtone(count);
        if (rt != null)
            rt.play();
    }

    @Override
    public void onListenerConnected() {
        super.onListenerConnected();
        Log.d("Service", "Connected");
    }

    @Override
    public void onListenerDisconnected() {
        super.onListenerDisconnected();
        Log.d("Service", "Disconnected");
    }

    @Override
    public void onDestroy() {
        Log.d("Service", "Destroyed");
        super.onDestroy();
    }

    private Ringtone getRingtone(int count) {
        switch (count) {
            case 0:
            case 1:
                return null;

            case 2:
                return doubleKill;
            case 3:
                    return tripleKill;
        }
        return overkill;
    }

    Ringtone doubleKill;
    Ringtone tripleKill;
    Ringtone overkill;


}

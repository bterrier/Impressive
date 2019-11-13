package net.gehngis.impressive;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

public class MyService extends NotificationListenerService {
    public MyService() {
        sounds = new Ringtone[9];
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sounds[0] = RingtoneManager.getRingtone(getApplicationContext(),
                Uri.parse("android.resource://net.gehngis.impressive/" + R.raw.double_kill));
        sounds[1] = RingtoneManager.getRingtone(getApplicationContext(),
                Uri.parse("android.resource://net.gehngis.impressive/" + R.raw.triple_kill));
        sounds[2] = RingtoneManager.getRingtone(getApplicationContext(),
                Uri.parse("android.resource://net.gehngis.impressive/" + R.raw.overkill));
        sounds[3] = RingtoneManager.getRingtone(getApplicationContext(),
                Uri.parse("android.resource://net.gehngis.impressive/" + R.raw.killtacular));
        sounds[4] = RingtoneManager.getRingtone(getApplicationContext(),
                Uri.parse("android.resource://net.gehngis.impressive/" + R.raw.killtrocity));
        sounds[5] = RingtoneManager.getRingtone(getApplicationContext(),
                Uri.parse("android.resource://net.gehngis.impressive/" + R.raw.killimanjaro));
        sounds[6] = RingtoneManager.getRingtone(getApplicationContext(),
                Uri.parse("android.resource://net.gehngis.impressive/" + R.raw.killtastrophe));
        sounds[7] = RingtoneManager.getRingtone(getApplicationContext(),
                Uri.parse("android.resource://net.gehngis.impressive/" + R.raw.killpocalypse));
        sounds[8] = RingtoneManager.getRingtone(getApplicationContext(),
                Uri.parse("android.resource://net.gehngis.impressive/" + R.raw.killionaire));
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
        if (count < 2)
            return null;

        int index = count - 2; //Double kill is at index 0
        if (index >= sounds.length)
            index = sounds.length - 1;

        return sounds[index];
    }

    Ringtone sounds[];


}

package com.example.auraia.Statistics;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.myapplication.R;


public class wakeupAlert extends ContextWrapper {

    public static final String channelIDWake = "channelID";
    public static final String channelNameWake = "Channel Name";
    Intent it;
    PendingIntent p;

    public wakeupAlert(Context base) {
        super(base);
        createChannel();
    }
    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(channelIDWake, channelNameWake, NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(channel);
    }

    public NotificationCompat.Builder getChanNotification(Context base) {
        it = new Intent(base, wakeStatsCheck.class);
        p = PendingIntent.getActivity(base, 0, it,0);
        return new NotificationCompat.Builder(getApplicationContext(), channelIDWake)
                .setContentTitle("hope you slept well ♥")
                .setContentText("let's put your stats in the tracker!!")
                .setSmallIcon(R.drawable.picture1)
                // .setOngoing(true)
                .setContentIntent(p);
    }

    private NotificationManager mbManager;
    public NotificationManager getManager() {
        if (mbManager == null) {
            mbManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mbManager;
    }


}


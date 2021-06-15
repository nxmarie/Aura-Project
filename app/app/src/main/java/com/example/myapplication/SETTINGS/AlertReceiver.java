package com.example.myapplication.SETTINGS;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.example.myapplication.Dimming.ScreenFIlterService;
import com.example.myapplication.MainActivity;

public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        NotifHelp notificationHelper = new NotifHelp(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
        notificationHelper.getManager().notify(1, nb.build());
    }
}

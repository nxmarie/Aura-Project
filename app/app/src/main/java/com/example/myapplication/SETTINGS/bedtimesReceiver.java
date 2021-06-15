package com.example.myapplication.SETTINGS;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.example.myapplication.Statistics.wakeupAlert;


public class bedtimesReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //if the time has passed and the alarm is dunout then select the bedtime first babae
        wakeupAlert nHelper = new wakeupAlert(context);
        NotificationCompat.Builder nb = nHelper.getChanNotification(context);
        nHelper.getManager().notify(1, nb.build());
    }

}

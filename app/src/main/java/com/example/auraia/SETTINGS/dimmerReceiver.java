package com.example.auraia.SETTINGS;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.auraia.Dimming.ScreenFIlterService;

public class dimmerReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, ScreenFIlterService.class);
        if (ScreenFIlterService.STATE==ScreenFIlterService.STATE_INACTIVE){
            context.startService(i);
        }

    }


}


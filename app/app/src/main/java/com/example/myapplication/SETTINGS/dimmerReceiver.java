package com.example.myapplication.SETTINGS;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.myapplication.Dimming.ScreenFIlterService;

public class dimmerReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, ScreenFIlterService.class);
        if (ScreenFIlterService.STATE==ScreenFIlterService.STATE_INACTIVE){
            context.startService(i);
        }

    }


}


package com.example.myapplication.BackgroundNoises;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.myapplication.BackgroundNoises.noiseService;
import com.example.myapplication.Dimming.ScreenFIlterService;

public class musicOff extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent fidem = new Intent(context, noiseService.class);
        context.stopService(fidem);

    }
}

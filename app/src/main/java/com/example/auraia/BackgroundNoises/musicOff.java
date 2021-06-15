package com.example.auraia.BackgroundNoises;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class musicOff extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent fidem = new Intent(context, noiseService.class);
        context.stopService(fidem);

    }
}

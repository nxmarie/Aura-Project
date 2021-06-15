package com.example.myapplication.SETTINGS;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.widget.Toast;


public class muteOffReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        AudioManager audioManager =(AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
      //  Toast.makeText(context, "muted", Toast.LENGTH_SHORT).show();

    }
}

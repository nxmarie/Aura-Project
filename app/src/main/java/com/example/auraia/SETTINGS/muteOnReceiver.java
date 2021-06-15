package com.example.auraia.SETTINGS;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;

public class muteOnReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        AudioManager audioManager =(AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);

    }
}

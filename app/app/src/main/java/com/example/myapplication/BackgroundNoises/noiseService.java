package com.example.myapplication.BackgroundNoises;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.myapplication.Journal.JournalsIn.journalEnt;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

import java.util.LinkedList;

import static com.example.myapplication.BackgroundNoises.musicActivity.AUDIO;
import static com.example.myapplication.BackgroundNoises.musicActivity.mPlay;
import static com.example.myapplication.BackgroundNoises.musicActivity.play;

public class noiseService extends Service{
    public static final String CHANNEL_ID = "tuneServiceChannel";

    private MediaPlayer bgPlayer;
    @Override
    public void onCreate() {

    }

    //@Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override

    public int onStartCommand(Intent intent, int flags, int startId) {
        //super.onStartCommand(intent, flags, startId);

        Toast.makeText(getApplicationContext(), "now playing :)",    Toast.LENGTH_SHORT).show();
        createNotifChannel();

        SharedPreferences npref = this.getSharedPreferences(AUDIO, Context.MODE_PRIVATE);
        int tune = npref.getInt("MP3",R.raw.lofi);
        super.onCreate();
        bgPlayer = MediaPlayer.create(this,tune);
        bgPlayer.setLooping(true);
        bgPlayer.start();
        Intent it = new Intent(this, musicActivity.class);
        PendingIntent p = PendingIntent.getActivity(this, 0, it,0);
        mPlay = true;
        play.setImageResource(R.drawable.pausebtn);

        Notification notif = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentText("Just some tunes to help you sleep :)")
                .setContentTitle("now playing")
                .setSmallIcon(R.drawable.picture1)
                .setContentIntent(p).build();

        startForeground(1,notif);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bgPlayer.stop();
        bgPlayer.release();
        play.setImageResource(R.drawable.playbtn);
        mPlay = false;
        stopForeground(true);
        stopSelf();
    }

    private void createNotifChannel() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel nChan = new NotificationChannel
                    (CHANNEL_ID, "Forground foreskin",
                            NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager m = getSystemService(NotificationManager.class);
            m.createNotificationChannel(nChan);
        }
    }

}


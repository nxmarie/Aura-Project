package com.example.auraia.SETTINGS;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.myapplication.R;

import java.util.Random;

public class NotifHelp extends ContextWrapper {
    public static final String channelID = "channelID";
    public static final String channelName = "Channel Name";
    private final String[] notifContent = {
            "you are loved and appreciated. you matter",
            "don't forget to drink some water today!",
            "you deserve the world and so much more <3",
            "in case nobody told you today, you're beautiful ♥",
            "today is a new opportunity.... your past doesn't define you",
            "you're amazing, have a great day :)",
            "you've got this! I believe in you :)",
            "take care of yourself, if you need to rest, REST",
            "smile! you're so cute when you smile",
            "people are temporary, focus on yourself",
            "hi! if you haven't eaten yet, go do so...your body needs it!",
            "you're royalty, know your worth <3",
            "it's okay to feel down sometimes, your emotions are valid",
            "you are enough. don't let anybody make you feel otherwise",
            "set your boundaries, protect you energy, keep moving",
            "be kind to yourself, you deserve to feel loved",
            "you are beautiful and unique just the way you are. your life is so precious ",
            "if you didn't hear me the first time, take! care! of! yourself! please",
            "some things are out of your control, try to come to peace with your surroundings",
            "your mistakes don't define you, forgive yourself",
            "be proud of yourself! you made it through another day!",
            "don't compare yourself to others...you're perfect in your own way",
    };

    public NotifHelp(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }
    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel chan = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(chan);
    }

    public NotificationCompat.Builder getChannelNotification() {
        Random r = new Random();
        int rndmInt = r.nextInt(notifContent.length-1);
        return new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setContentTitle("love from Aura")
                .setContentText(notifContent[rndmInt])
                .setSmallIcon(R.drawable.picture1)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(notifContent[rndmInt]))
                ;
    }

    private NotificationManager mManager;
    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

}

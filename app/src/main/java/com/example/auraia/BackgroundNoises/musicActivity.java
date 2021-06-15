package com.example.auraia.BackgroundNoises;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.auraia.Intros.homeActivity;
import com.example.myapplication.R;

import java.util.Calendar;

public class musicActivity extends AppCompatActivity implements timerDialog.DialogListen {

    private Button homeBtn;
    private Button timerBtn;
    private Button lofiOpt;
    private Button radioOpt;
    private Button windOpt;
    private Button lofiOpt2;
    private Button rainOpt;
    private String time;
    public static final String AUDIO = "MUSIC_FILES";
    public static final String help = "MUSIC_FILES";

    public static ImageView play;
    public static boolean mPlay;
    private int setfr;
    private TextView txtSet;
    private timerDialog t;
    private static long START_TIME_IN_MILLIS;
    private TextView countCheck;

    private CountDownTimer cdt;
    private boolean mTimerRunning = false;
    private long mTimeLeftInMillis;
    private long mEndTime;
    private Calendar delete;


    public void homeActivityOpen() {
        Intent intent = new Intent(this, homeActivity.class);
        startActivity(intent);
        finish();
    }

    public void timerDialogOpen() {
        t = new timerDialog();
        t.show(getSupportFragmentManager(), "dialog");
    }
    //mPlay = false;
    Intent init;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_layout);
        init = new Intent(this, noiseService.class);
        txtSet = findViewById(R.id.timerSet);
        countCheck = findViewById(R.id.COUNT);
        setfr = R.raw.lofi;


        homeBtn = findViewById(R.id.homeBtnMusic);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeActivityOpen();
            }
        });

        timerBtn = findViewById(R.id.timerBtn);
        timerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timerDialogOpen();
            }
        });

        play = findViewById(R.id.playView);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSong(setfr);
            }
        });

        lofiOpt = findViewById(R.id.lofiSet);
        lofiOpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setfr = R.raw.lofi;
                sendSong(R.raw.lofi);
            }
        });

        lofiOpt2 = findViewById(R.id.lofiSet2);
        lofiOpt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setfr = R.raw.lofi2;
                sendSong(R.raw.lofi2);
            }
        });

        radioOpt = findViewById(R.id.radioSet);
        radioOpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setfr = R.raw.statics;
                sendSong(R.raw.statics);

            }
        });

        windOpt = findViewById(R.id.windSet);
        windOpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setfr = R.raw.wind;
                sendSong(R.raw.wind);
            }
        });

        rainOpt = findViewById(R.id.rainSet);
        rainOpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setfr = R.raw.rain;
                sendSong(R.raw.rain);
            }
        });
    }

    public void sendSong(int musicInt) {
        SharedPreferences musicpref = getBaseContext().getSharedPreferences(AUDIO, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = musicpref.edit();
        e.putInt("MP3", musicInt);
        e.apply();
        if (mPlay == true) {
            stopService(init);
            mPlay = false;
        } else {
            startForegroundService(init);
            mPlay = true;
        }
    }

    @Override
    public void applyTexts(int t, String textA) {
        time = "timer set for " + textA;
        if (mPlay == true) {
            Toast.makeText(getApplicationContext(), time, Toast.LENGTH_LONG).show();
            START_TIME_IN_MILLIS = t * 60000;
            int plustime = t;
            mTimeLeftInMillis = START_TIME_IN_MILLIS;
            delete = Calendar.getInstance();
            delete.add(Calendar.MINUTE, plustime);
            delete.set(Calendar.SECOND, 0);


            if (mTimerRunning == true) {
                resetTimer();
                timerStart(delete);
            } else {
                timerStart(delete);
                mTimerRunning = true;
            }
        }
    }

    private void timerStart(Calendar f) {
        Intent i = new Intent(this, musicOff.class);
        final int id3 = 1294;
        PendingIntent pIntent = PendingIntent.getBroadcast(this, 2, i, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        am.setExact(AlarmManager.RTC_WAKEUP, f.getTimeInMillis(), pIntent);

    }

    private void resetTimer() {
        cdt.cancel();
        cancelAlarm(delete);
    }

    public void cancelAlarm(Calendar k) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, musicOff.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 2, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences prefs = getSharedPreferences(help, MODE_PRIVATE);
        boolean playing = prefs.getBoolean("play", false);
        mPlay = playing;

        if (playing == false) {
            play.setImageResource(R.drawable.playbtn);
        } else {
            play.setImageResource(R.drawable.pausebtn);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences prefs = getSharedPreferences(help, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("play", mPlay);
        editor.apply();
        mTimerRunning = false;


    }
}

package com.example.auraia.SETTINGS;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.auraia.Dimming.dimActivity;
import com.example.auraia.Intros.homeActivity;
import com.example.myapplication.R;

import java.util.Calendar;

import static com.example.auraia.Journal.Entries.entriesActivity.ELIST;
import static com.example.auraia.SETTINGS.bedSettingsActivity.notif1;
import static com.example.auraia.SETTINGS.bedSettingsActivity.notif2;
import static com.example.auraia.SETTINGS.bedSettingsActivity.notif3;
import static com.example.auraia.SETTINGS.bedSettingsActivity.notif4;

public class settingsActivity extends AppCompatActivity {
    private Button dimBtn;
    private Button bedsetBtn;
    private Button homeBtn;
    private Button notifsBtn;
    private Button clearEntries;
    private Button clearStats;
    public static boolean enableNotifs;
    boolean pvsisible= false;
    private ProgressBar p;
    private boolean definite=false;
    public static boolean entClear;
    public static boolean statClear;
    public static final String XENTS = "ENTRIES_DELETE";
    public static final String CHECK = "NOTIFS";


    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences switchh = this.getSharedPreferences(CHECK, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = switchh.edit();
        e.putBoolean(XENTS, enableNotifs);
        e.apply();

    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences switchh = this.getSharedPreferences(CHECK, Context.MODE_PRIVATE);
        enableNotifs = switchh.getBoolean(XENTS,true);

        if(!enableNotifs){
            notifsBtn.setText("turn on notifications");
        }
        else {
            notifsBtn.setText("turn off notifications");

        }

    }

    public void homeActivityOpen() {
        Intent intent = new Intent(this, homeActivity.class);
        startActivity(intent);
        finish();
    }

    public void startAlarm(Calendar c, int idReal) {
        if(c!=null){
            Intent intent = new Intent(this, AlertReceiver.class);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, idReal, intent, 0);
            if (c.before(Calendar.getInstance())) {
                c.add(Calendar.DATE, 1);
            }
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY,pendingIntent);
        }

    }

    public void cancelAlarm(Calendar k, int idReal) {
        if (k!=null){
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(this, AlertReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, idReal, intent, 0);
            alarmManager.cancel(pendingIntent);
        }

    }

    public void dimActivityOpen() {
        Intent intent = new Intent(this, dimActivity.class);
        startActivity(intent);
        finish();
    }

    public void bedsetActivityOpen() {
        Intent intent = new Intent(this, bedSettingsActivity.class);
        startActivity(intent);
        finish();
    }

    public void switchNotifications(){
        if(enableNotifs==true){
            enableNotifs = false;
            notifsBtn.setText("turn on notifications");
            Toast.makeText(getBaseContext(), "notifications off", Toast.LENGTH_SHORT).show();

            notifsBtn.setText("turn on notifications");
            cancelAlarm(notif1,206);
            cancelAlarm(notif2,584);
            cancelAlarm(notif3, 910);
            cancelAlarm(notif4, 763);
        }
        else{
            enableNotifs = true;
            notifsBtn.setText("turn off notifications");
            Toast.makeText(getBaseContext(), "notifications on", Toast.LENGTH_SHORT).show();
            startAlarm(notif1,206);
            startAlarm(notif2,584);
            startAlarm(notif3, 910);
            startAlarm(notif4, 763);
        }

    }

    public void deleteEntries(){
        new AlertDialog.Builder(this)
                .setTitle("Are you sure")
                .setMessage("This cannot be undone!!")
                .setPositiveButton("delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        entClear = true;
                        SharedPreferences updateList = getSharedPreferences(ELIST, Context.MODE_PRIVATE);
                        SharedPreferences.Editor e = updateList.edit();
                        e.clear();
                        e.commit();

                        Toast.makeText(getBaseContext(), "entries deleted!", Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        entClear = false;

                    }
                })
                .create().show();
    }

    public void deleteStats(){
        new AlertDialog.Builder(this)
                .setTitle("Are you sure you want to clear statistics?")
                .setMessage("This cannot be undone!!")
                .setPositiveButton("delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        statClear = true;
                        Toast.makeText(getBaseContext(), "statisics deleted!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        statClear = false;

                    }
                })
                .create().show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);

        notifsBtn =findViewById(R.id.notifcheckBtn);
        notifsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchNotifications();
            }
        });

        dimBtn = findViewById(R.id.dimsetBtn);
        dimBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dimActivityOpen();
            }
        });

        clearEntries = findViewById(R.id.clearJournalsBtn);
        clearEntries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEntries();
            }
        });

        clearStats = findViewById(R.id.clearStatsBtn);
        clearStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteStats();
            }
        });

        homeBtn = findViewById(R.id.homesetBtn);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeActivityOpen();
            }
        });

        bedsetBtn = findViewById(R.id.bedsetBtn);
        bedsetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bedsetActivityOpen();
            }
        });

    }
}
package com.example.myapplication.SETTINGS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.myapplication.Intros.homeActivity;
import com.example.myapplication.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.myapplication.SETTINGS.settingsActivity.enableNotifs;

public class bedSettingsActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener{
    private TextView bedTimeTxt;
    private TextView wakeTimeTxt;
    private TextView notifsOnTxt;
    private TextView notifsOffTxt;
    private TextView filterOnTxt;
    private TextView filterOffTxt;
    private boolean bedClick;
    private boolean wakeClick;
    private boolean notifNull1=false;
    private boolean notifNull2=false;

    private String sleep_time;
    private String wake_time;
    private String filton;
    private String filtoff;
    private String onString;
    private String offString;


    private Calendar filterOn;
    private Calendar notifOn;
    private Calendar filterOff;
    private Calendar notifOff;

    public static Calendar wakeup;
    public static Calendar bedtime;

    public static boolean timeDone = false;

    public static Calendar notif3;
    public static Calendar notif2;
    public static Calendar notif1;
    public static Calendar notif4;

    public static final String BEDLIST = "BEDTIMES";
    private NotificationManagerCompat notificationManager;

    public void homeActivityOpen() {
        Intent intent = new Intent(this, homeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        notificationManager = NotificationManagerCompat.from(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.bedset_layout);

        bedTimeTxt = findViewById(R.id.bedTimeTxt);
        wakeTimeTxt = findViewById(R.id.wakeTimeTxt);
        bedClick = false;
        wakeClick = false;

        Button setBedtimeBtn = findViewById(R.id.setBedBtn);
        setBedtimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bedClick = true;
                DialogFragment timePicker = new timePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });


        Button setWakeupBtn = findViewById(R.id.setWakeupBtn);
        setWakeupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wakeClick = true;
                DialogFragment timePicker = new timePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        notifsOffTxt = findViewById(R.id.notifsOffTxt);
        notifsOnTxt = findViewById(R.id.notifsOnTxt);
        filterOffTxt = findViewById(R.id.filterOffTxt);
        filterOnTxt = findViewById(R.id.filterOnTxt);

        Button homeBtn = findViewById(R.id.homeBtnbed);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeActivityOpen();
            }
        });
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        SimpleDateFormat changeTime = new SimpleDateFormat("HH:mm");
        String sTime = (hourOfDay + ":" + minute);

        if (bedClick == true){

            try {
                Date mTime = changeTime.parse(sTime);
                String out = changeTime.format(mTime);
                sleep_time = out;
                bedTimeTxt.setText(sleep_time);

                bedtime = Calendar.getInstance();
                bedtime.set(Calendar.HOUR_OF_DAY, hourOfDay);//set multiple alarms
                bedtime.set(Calendar.MINUTE, minute);
                bedtime.set(Calendar.SECOND, 0);

                notifOff = Calendar.getInstance();
                notifOff.set(Calendar.HOUR_OF_DAY, hourOfDay);//set multiple alarms
                notifOff.set(Calendar.MINUTE, minute-20);
                notifOff.set(Calendar.SECOND, 0);


                filterOn = Calendar.getInstance();
                filterOn.set(Calendar.HOUR_OF_DAY, hourOfDay-1);//set multiple alarms
                filterOn.set(Calendar.MINUTE, minute);
                filterOn.set(Calendar.SECOND, 0);

                notif1 = Calendar.getInstance();
                notif1.set(Calendar.HOUR_OF_DAY, hourOfDay);//set multiple alarms
                notif1.set(Calendar.MINUTE, minute-10);
                notif1.set(Calendar.SECOND, 0);


                String newTimeFilterOn = changeTime.format(filterOn.getTime());
                filterOnTxt.setText("filter on - "+ newTimeFilterOn);
                filton = filterOnTxt.getText().toString();

                String newTimeNotifOff = changeTime.format(notifOff.getTime());
                notifsOffTxt.setText("notifications off - "+ newTimeNotifOff);
                offString = notifsOffTxt.getText().toString();

                notifNull2 = true;

            } catch (ParseException e) {
            }bedClick = false;
        }
        else{
            if (wakeClick == true) {
                notifOn = Calendar.getInstance();
                notifOn.set(Calendar.HOUR_OF_DAY, hourOfDay);//set multiple alarms
                notifOn.set(Calendar.MINUTE, minute-20);
                notifOn.set(Calendar.SECOND, 0);

                String newTimeNotifOn = changeTime.format(notifOn.getTime());
                notifsOnTxt.setText("notifications on - "+ newTimeNotifOn);
                onString = notifsOnTxt.getText().toString();

                filterOff = Calendar.getInstance();
                filterOff.set(Calendar.HOUR_OF_DAY, hourOfDay-1);//set multiple alarms
                filterOff.set(Calendar.MINUTE, minute);
                filterOff.set(Calendar.SECOND, 0);

                notif4 = Calendar.getInstance();
                notif4.set(Calendar.HOUR_OF_DAY, hourOfDay);//set multiple alarms
                notif4.set(Calendar.MINUTE, minute+10);
                notif4.set(Calendar.SECOND, 0);

                String newTimeFilterOff = changeTime.format(filterOff.getTime());
                filterOffTxt.setText("filter off - "+ newTimeFilterOff);
                filtoff = filterOffTxt.getText().toString();


                wakeup = Calendar.getInstance();
                wakeup.set(Calendar.HOUR_OF_DAY, hourOfDay);//set multiple alarms
                wakeup.set(Calendar.MINUTE, minute);
                wakeup.set(Calendar.SECOND, 0);

                try {
                    Date mTime = changeTime.parse(sTime);
                    String out = changeTime.format(mTime);
                    wake_time = out;
                    wakeTimeTxt.setText(out);
                    notifNull1 = true;

                } catch (ParseException e) { }

            }
            wakeClick = false;
        }

        if (notifNull1 == true && notifNull2 == true)
        {
            notif2 = Calendar.getInstance();
            notif2.set(Calendar.HOUR_OF_DAY, notifOn.get(Calendar.HOUR_OF_DAY)+3);
            notif2.set(Calendar.MINUTE, notifOn.get(Calendar.MINUTE));
            notif2.set(Calendar.SECOND, 0);


            notif3 = Calendar.getInstance();
            notif3.set(Calendar.HOUR_OF_DAY, notifOff.get(Calendar.HOUR_OF_DAY)-5);
            notif3.set(Calendar.MINUTE, notifOn.get(Calendar.MINUTE)-17);
            notif3.set(Calendar.SECOND, 0);

            if(enableNotifs!=false){
                startAlarm(notif1, 206);
                startAlarm(notif2,584);
                startAlarm(notif3, 910);
                startAlarm(notif4, 763);
            }

            filterAlarm(filterOn,23);
            filterOffAlarm(filterOff,2351);
            notifsOffAlarm(notifOff,67);
            notifsOnAlarm(notifOn,3694);
            sleepTimesAlarm(wakeup,7985);

            notifNull1 = false;
            notifNull2 = false;
            timeDone = false;

        }
    }

    private void sleepTimesAlarm(Calendar c, int idReal) {
        Intent intent = new Intent(this, bedtimesReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, idReal, intent,0);
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY,pendingIntent);
    }

    public void startAlarm(Calendar c, int idReal) {
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, idReal, intent,0);
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY,pendingIntent);
    }


    private void filterAlarm(Calendar f, int idReal){
        Intent i = new Intent(this, dimmerReceiver.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(this, idReal, i, 0);
        if (f.before(Calendar.getInstance())) {
            f.add(Calendar.DATE, 1);
        }
        AlarmManager a = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        a.setRepeating(AlarmManager.RTC_WAKEUP, f.getTimeInMillis(), AlarmManager.INTERVAL_DAY,pIntent);

    }

    private void filterOffAlarm(Calendar f, int idReal){
        Intent i = new Intent(this, dimmerOffReceiver.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(this, idReal, i, 0);
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (f.before(Calendar.getInstance())) {
            f.add(Calendar.DATE, 1);
        }
        am.setRepeating(AlarmManager.RTC_WAKEUP, f.getTimeInMillis(), AlarmManager.INTERVAL_DAY,pIntent);
    }

    private void notifsOffAlarm(Calendar f, int idReal){
        Intent i = new Intent(this, muteOffReceiver.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(this, idReal, i, 0);
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, f.getTimeInMillis(), AlarmManager.INTERVAL_DAY,pIntent);
    }

    private void notifsOnAlarm(Calendar f, int idReal){
        Intent i = new Intent(this, muteOnReceiver.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(this, idReal, i, 0);
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, f.getTimeInMillis(), AlarmManager.INTERVAL_DAY,pIntent);

    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences bedroom = this.getSharedPreferences(BEDLIST, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = bedroom.edit();
        e.putString("WAKEUP", wake_time);
        e.putString("BEDTIME",sleep_time);
        e.putString("FILTERON",filton);
        e.putString("FILTEROFF",filtoff);
        e.putString("NOTIFON",onString);
        e.putString("NOTIFOFF",offString);

        e.apply();

    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences bedroom = this.getSharedPreferences(BEDLIST, Context.MODE_PRIVATE);
        sleep_time = bedroom.getString("BEDTIME","00:00");
        wake_time = bedroom.getString("WAKEUP","00:00");
        onString = bedroom.getString("NOTIFON","notifications on - " );
        offString = bedroom.getString("NOTIFOFF","notifications off -" );
        filton = bedroom.getString("FILTERON","filter on - " );
        filtoff = bedroom.getString("FILTEROFF","filter off - " );

        bedTimeTxt = findViewById(R.id.bedTimeTxt);
        wakeTimeTxt = findViewById(R.id.wakeTimeTxt);
        notifsOnTxt = findViewById(R.id.notifsOnTxt);
        notifsOffTxt= findViewById(R.id.notifsOffTxt);
        filterOnTxt= findViewById(R.id.filterOnTxt);
        filterOffTxt= findViewById(R.id.filterOffTxt);

        bedTimeTxt.setText(sleep_time);
        wakeTimeTxt.setText(wake_time);
        notifsOnTxt.setText(onString);
        notifsOffTxt.setText(offString);
        filterOnTxt.setText(filton);
        filterOffTxt.setText(filtoff);
    }
}
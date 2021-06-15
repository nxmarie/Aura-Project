package com.example.auraia.Statistics;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.auraia.Statistics.bedTimePicker;
import com.example.auraia.Statistics.wakeTimePicker;
import com.example.auraia.Intros.homeActivity;
import com.example.myapplication.R;
import com.example.auraia.SETTINGS.settingsActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import static com.example.auraia.Journal.Entries.entriesActivity.ELIST;
import static com.example.auraia.Statistics.statsActivity.STATLIST;

public class wakeStatsCheck  extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener{

    private TextView wakeCalc;
    private TextView bedCalc;
    private TextView sleepCalcFinal;

    private int mins1;

    private LinkedList<Integer> sleepList = new LinkedList<>();

    private Button homeCalcDem;
    private Button doneBtn;

    private String outbed;
    private String outwake;

    private Calendar bed;
    private Calendar wakeup;

    private boolean calcNull;
    private boolean calcNull2;
    private boolean set;
    private boolean clearCheck3;


    private boolean bedClick1;
    private boolean wakeClick1;

    public void homeActivityOpen() {
        Intent intent = new Intent(this, homeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wakestats_layout);


        SharedPreferences statsDem = this.getSharedPreferences(STATLIST, Context.MODE_PRIVATE);
        clearCheck3 = settingsActivity.statClear;
        if(clearCheck3==true){
            sleepList.clear();
            sleepList.clear();
            sleepList = new LinkedList<Integer>();
            statsDem.edit().clear();
            statsDem.edit().apply();

            clearCheck3=false;
            settingsActivity.statClear=false;
        }

        wakeCalc = findViewById(R.id.wakeCalc);
        bedCalc = findViewById(R.id.bedCalcQ);
        sleepCalcFinal = findViewById(R.id.sleepCalcFinal);
        homeCalcDem = findViewById(R.id.homeCalcDem);
        doneBtn = findViewById(R.id.doneBtnStat);
        doneBtn.setVisibility(View.INVISIBLE);

        bedClick1 = false;
        wakeClick1 = false;

        homeCalcDem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeActivityOpen();
                finish();
            }
        });



        bedCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bedClick1 = true;
                DialogFragment timePicker = new bedTimePicker();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        wakeCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wakeClick1 = true;
                DialogFragment timePicker = new wakeTimePicker();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });


    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        SimpleDateFormat changeTime = new SimpleDateFormat("HH:mm");
        String sTime = (hourOfDay + ":" + minute);
        if (bedClick1 == true) {

            bed = Calendar.getInstance();
            bed.add(Calendar.DAY_OF_YEAR, -1);

            bed.set(Calendar.HOUR_OF_DAY, hourOfDay);//set multiple alarms
            bed.set(Calendar.MINUTE, minute);
            bed.set(Calendar.SECOND, 0);

            try {
                Date mTime = changeTime.parse(sTime);
                String out = changeTime.format(mTime);
                outbed = out;
                bedCalc.setText(out);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            bedClick1 = false;
            calcNull = true;


        }

        if (wakeClick1 == true) {

            wakeup = Calendar.getInstance();
            wakeup.set(Calendar.HOUR_OF_DAY, hourOfDay);//set multiple alarms
            wakeup.set(Calendar.MINUTE, minute);
            wakeup.set(Calendar.SECOND, 0);

            try {
                Date mTime = changeTime.parse(sTime);
                String out = changeTime.format(mTime);
                outwake = out;
                wakeCalc.setText(out);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            wakeClick1 = false;
            calcNull2 = true;

        }

        if (calcNull == true && calcNull2 == true)
        {

            if(wakeup.before(bed)){
                bed.add(Calendar.DAY_OF_YEAR, -1);
            }

            String bedDem = bedCalc.getText().toString();
            bedDem = bedDem.substring(0,2);




            long seconds = (wakeup.getTimeInMillis() - bed.getTimeInMillis()) / 1000;
            mins1 = (int) (seconds / 60);
           // sleepCalcFinal.setText(mins1+" minutes");



            if(mins1>1440){
                mins1= mins1-1440;
            }


            int hours;
            hours = mins1/60;
            /*if(bedDem.equals("01")|| (bedDem.equals("00"))){
                hours = hours+1;
            }*/
            int minsDem = mins1 % 60;



            sleepCalcFinal.setText(hours + "h "+minsDem+"mins");
           // sleepCalcFinal.setText(bedDem);
            doneBtn.setVisibility(View.VISIBLE);
            doneBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sleepList.add(mins1);
                    Toast.makeText(getBaseContext(), "sleep time added!", Toast.LENGTH_SHORT).show();

                    storeList(sleepList);

                }

            });
            set = true;

        }
        if(set){
            calcNull = false;
            calcNull2 = false;
            set= false;
        }

    }

    private void storeList(LinkedList<Integer> sleepList) {
        SharedPreferences statsDem = this.getSharedPreferences(STATLIST, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = statsDem.edit();
        e.putString("Jva",setList(sleepList));
        e.putString("bedtime", outbed );
        e.putString("wakeup", outwake);
        e.commit();
    }


    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences statsDem = this.getSharedPreferences(STATLIST, Context.MODE_PRIVATE);
        String bed = statsDem.getString("bedtime","bedtime");
        String awake = statsDem.getString("wakeup","wakeup time");
        bedCalc.setText(bed);
        wakeCalc.setText(awake);

        String obj1 = statsDem.getString("Jva",null);
        if (obj1 != null){
            Gson g = new Gson();
            Type t = new TypeToken<LinkedList<Integer>>(){}.getType();
            LinkedList<Integer> olderStats = g.fromJson(obj1, t);
            sleepList = olderStats;
        }

    }


    private <Integer> String setList(LinkedList<Integer> list){
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }




}

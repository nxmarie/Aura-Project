package com.example.auraia.Statistics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.auraia.Statistics.wakeStatsCheck;
import com.example.auraia.Intros.homeActivity;
import com.example.auraia.Journal.JournalsIn.journalEnt;
import com.example.myapplication.R;
import com.example.auraia.SETTINGS.settingsActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.LinkedList;

import static com.example.auraia.Journal.Entries.entriesActivity.ELIST;

public class statsActivity extends AppCompatActivity {
    public static final String STATLIST = "string constant";

    private Button homeBtn;

    LinkedList<Integer> moodInts = new LinkedList<>();
    LinkedList<Integer> sleepTimes = new LinkedList<>();


    private Button sleepy;
    private TextView moodtxt;
    private TextView msg;
    private String moodNA="";
    private boolean clearCheck3;

    private String sleepNA;

    private TextView sleeptxt;

    public void homeActivityOpen() {
        Intent intent = new Intent(this, homeActivity.class);
        startActivity(intent);
        finish();
    }

    public void wakeOpen() {
        Intent intent = new Intent(this, wakeStatsCheck.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences statsDem = this.getSharedPreferences(STATLIST, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = statsDem.edit();
        e.putString("Jva",setList3(sleepTimes));
        e.commit();

    }

    private <Integer> String setList3(LinkedList<Integer> list){
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    @Override
    protected void onCreate(Bundle inst) {
        super.onCreate(inst);
        setContentView(R.layout.stats_layout);

        moodtxt = findViewById(R.id.moodAvgTxt);
        sleeptxt = findViewById(R.id.SleepAvgTxt);
        msg = findViewById(R.id.msgTxt);
        sleepy = findViewById(R.id.sleepstats);

        SharedPreferences statsDem = this.getSharedPreferences(STATLIST, Context.MODE_PRIVATE);
        String obj1 = statsDem.getString("Jva",null);
        if (obj1 != null){
            Gson g = new Gson();
            Type t = new TypeToken<LinkedList<Integer>>(){}.getType();
            LinkedList<Integer> olderStats = g.fromJson(obj1, t);
            sleepTimes = olderStats;
        }

        clearCheck3 = settingsActivity.statClear;
        if(clearCheck3==true){
            sleepTimes.clear();
            sleepTimes = new LinkedList<Integer>();
            SharedPreferences.Editor e = statsDem.edit();
            e.clear();
            e.commit();

            //clearCheck3=false;
           // settingsActivity.statClear=false;
        }



        SharedPreferences mySharedPreferences = this.getSharedPreferences(ELIST, Context.MODE_PRIVATE);
        String obj = mySharedPreferences.getString("JSON",null);
        if (obj != null){
            Gson g = new Gson();
            Type t = new TypeToken<LinkedList<journalEnt>>(){}.getType();
            LinkedList<journalEnt> newMoods = g.fromJson(obj, t);

            for (int i = 0; i<newMoods.size(); i++){
                moodInts.add(newMoods.get(i).getMood());
            }
        }


        double total = 0;
        double avg=0;
        int moodsize = moodInts.size();
        if(moodsize!=0){
            for(int i=0;i<moodsize;i++){
                total=total+moodInts.get(i);
            }
            avg= total/moodInts.size();
            avg = (double) Math.round(avg * 100) / 100;
            moodtxt.setText(String.valueOf(avg));
        }
        else{
            moodtxt.setText("N/A");
            moodNA = moodtxt.getText().toString();
        }

        int sleepTotal = 0;
        int sleepAvg;

        int sleepSize = sleepTimes.size();
        if(sleepSize!=0){
            for(int i=0;i<sleepSize;i++){
                sleepTotal=sleepTotal+sleepTimes.get(i);
            }
            sleepAvg= sleepTotal/ sleepSize;
            sleepAvg = (int) Math.round(sleepAvg * 100) / 100;
            int hours = sleepAvg/60;
            int minsDem = sleepAvg % 60;

            sleeptxt.setText(hours + "h "+minsDem+"mins");
        }
        else{
            sleeptxt.setText("N/A");
            sleepNA = sleeptxt.getText().toString();
        }


        msg.setText("I hope you have a good day! Sleep well and take care :)");

        if(!moodNA.equals("N/A")){
            if(avg<3.4){
                msg.setText("you seem to be having quite a few bad days. I know it's hard, but try to take care of yourself..." +
                        "take a break, reach out to someone if you can, I hope you feel better soon ♥.");
            }
            else {
                if(avg>3.9){
                    msg.setText("you seem to be having alot of good days. " +
                            "That's amazing!! I hope life continues to treat you well");
                }
            }
        }


        homeBtn = findViewById(R.id.homeCalcDem);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeActivityOpen();
            }
        });

        sleepy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wakeOpen();
            }
        });

    }



    private static double rounded (double value, int decimals) {
        int scale = (int) Math.pow(10, decimals); // 10 to the power2
        return (double) Math.round(value * scale) / 100; // rounds the value to an integer
    }

    public <journalEnt> String setList(LinkedList<journalEnt> list){
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }







}


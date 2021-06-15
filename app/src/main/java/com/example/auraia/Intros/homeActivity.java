package com.example.auraia.Intros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import com.example.auraia.BackgroundNoises.musicActivity;
import com.example.auraia.Dimming.ScreenFIlterService;
import com.example.auraia.Journal.JournalsIn.journalActivity;
import com.example.auraia.SETTINGS.settingsActivity;
import com.example.auraia.Statistics.statsActivity;
import com.example.myapplication.R;

public class homeActivity extends AppCompatActivity {
    public void musicActivityOpen() {
        Intent intent = new Intent(this, musicActivity.class);
        startActivity(intent);
    }

    public void journalActivityOpen() {
        Intent intent = new Intent(this, journalActivity.class);
        startActivity(intent);
    }

    public void statsActivityOpen() {
        Intent intent = new Intent(this, statsActivity.class);
        startActivity(intent);
    }

    public void settingsActivityOpen() {
        //startServiceToGetRecentApps();
        Intent intent = new Intent(this, settingsActivity.class);
        startActivity(intent);
    }

    public void helpActivityOpen() {
        Intent intent = new Intent(this, introActivity.class);
        startActivity(intent);

    }

    public void helpActivityOpen1() {
        SharedPreferences startprefs = getSharedPreferences("START",Context.MODE_PRIVATE);
        SharedPreferences.Editor e = startprefs.edit();
        e.putBoolean("FIRST",false);
        e.apply();

        Intent intent = new Intent(this, introActivity.class);
        startActivity(intent);

    }

    public void toggleDimmer(){
        Intent dimIntent = new Intent(this, ScreenFIlterService.class);


        if (ScreenFIlterService.STATE == ScreenFIlterService.STATE_ACTIVE){
            stopService(dimIntent);
        }
        else{
            startService(dimIntent);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        SharedPreferences startprefs = getSharedPreferences("START",Context.MODE_PRIVATE);
        boolean firstTime = startprefs.getBoolean("FIRST",true);

        if(firstTime){
            helpActivityOpen1();
        }


        Switch shortcut = findViewById(R.id.dimmerShortcut);
        shortcut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleDimmer();
            }
        });

        Button noiseBtn = findViewById(R.id.noiseScreenBtn);
        noiseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicActivityOpen();
            }
        });


        Button statsBtn = findViewById(R.id.statsBtn);
        statsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statsActivityOpen();
            }
        });

        Button journalBtn = findViewById(R.id.journalBtn);
        journalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                journalActivityOpen();
            }
        });

        Button helpBtn = findViewById(R.id.helpScreenBtn);
        helpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpActivityOpen();
            }
        });

        Button settingsBtn = findViewById(R.id.settingsBtn);
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingsActivityOpen();
            }
        });

    }





}
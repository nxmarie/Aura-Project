package com.example.myapplication.Dimming;


import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.Intros.homeActivity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.CountDownTimer;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

public class dimActivity extends AppCompatActivity {
    private SharedMemory mSharedMemory;
    private ImageView imgBtnWhite;
    private ImageView imgBtnBlue;
    private ToggleButton mToggleButton;
    private CountDownTimer mTimer;
    private Button homeBtn;
    private int t = 1;

    public void homeActivityOpen() {
        Intent intent = new Intent(this, homeActivity.class);
        startActivity(intent);
        finish();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dimming_layout);

        final SeekBar alpha, red;
        alpha  = findViewById(R.id.seekalpha);
        red  = findViewById(R.id.seekred);

        homeBtn = findViewById(R.id.homeBtndim);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeActivityOpen();
            }
        });

        imgBtnWhite= findViewById(R.id.toggleWhite);
        imgBtnBlue= findViewById(R.id.toggleBlue);

        if (ScreenFIlterService.STATE == ScreenFIlterService.STATE_INACTIVE){
            imgBtnWhite.setVisibility(View.VISIBLE);
            imgBtnBlue.setVisibility(View.INVISIBLE);
        }
        else{
            imgBtnWhite.setVisibility(View.INVISIBLE);
            imgBtnBlue.setVisibility(View.VISIBLE);

        }

        mToggleButton = findViewById(R.id.toggleButton);
        mSharedMemory = new SharedMemory(this);

        SeekBar.OnSeekBarChangeListener changeListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mSharedMemory.setAlpha(alpha.getProgress());
                mSharedMemory.setRed(red.getProgress());

                if(ScreenFIlterService.STATE == ScreenFIlterService.STATE_ACTIVE) {

                    Intent i = new Intent(dimActivity.this, ScreenFIlterService.class);
                    startService(i);
                }

                mToggleButton.setChecked(ScreenFIlterService.STATE==ScreenFIlterService.STATE_ACTIVE);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };

        alpha.setOnSeekBarChangeListener(changeListener);
        red.setOnSeekBarChangeListener(changeListener);

        mToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(dimActivity.this, ScreenFIlterService.class);
                if (ScreenFIlterService.STATE == ScreenFIlterService.STATE_ACTIVE){
                    stopService(i);
                    imgBtnWhite.setVisibility(View.VISIBLE);
                    imgBtnBlue.setVisibility(View.INVISIBLE);
                }

                else{
                    startService(i);
                    imgBtnWhite.setVisibility(View.INVISIBLE);
                    imgBtnBlue.setVisibility(View.VISIBLE);
                }
                refresh();
            }
        });
    }

    private void refresh() {
        if (mTimer != null)
            mTimer.cancel();

        mTimer = new CountDownTimer(100,100) {
            @Override
            public void onTick(long millisUntilFinished) {
            }
            @Override
            public void onFinish() {
                mToggleButton.setChecked(ScreenFIlterService.STATE==ScreenFIlterService.STATE_ACTIVE);
            }
        };
        mTimer.start();
    }
    @Override
    protected void onResume(){
        super.onResume();
        mToggleButton.setChecked(ScreenFIlterService.STATE==ScreenFIlterService.STATE_ACTIVE);
        if (ScreenFIlterService.STATE == ScreenFIlterService.STATE_INACTIVE){
            imgBtnWhite.setVisibility(View.VISIBLE);
            imgBtnBlue.setVisibility(View.INVISIBLE);
        }
        else{
            imgBtnWhite.setVisibility(View.INVISIBLE);
            imgBtnBlue.setVisibility(View.VISIBLE);
        }
    }

}
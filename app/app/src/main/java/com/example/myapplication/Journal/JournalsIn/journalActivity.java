package com.example.myapplication.Journal.JournalsIn;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.gson.Gson;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Intros.homeActivity;
import com.example.myapplication.Journal.Entries.entriesActivity;
import com.example.myapplication.R;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;

import static com.example.myapplication.Journal.Entries.entriesActivity.ELIST;

public class journalActivity extends AppCompatActivity {

    int moodInt = 0;
    int imageVal = R.drawable.mood0;
    String feelings = "";
    String dateM = "";
    private Button homeBtn;
    private Button entriesBtn;
    private EditText feelTxt;
    private Button saveBtn;
    private TextView mood;

    LinkedList<Integer> moodsList = new LinkedList<Integer>();
    LinkedList<journalEnt> entries = new LinkedList<journalEnt>();

    public void saveEntry(){
        SharedPreferences msp = getSharedPreferences(ELIST, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = msp.edit();
        e.putString("JSON",setList(entries));
        e.commit();
    }

    public <journalEnt> String setList(LinkedList<journalEnt> list){
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }


    @Override
    public void onStart(){
        super.onStart();
        SharedPreferences updateList = getSharedPreferences(ELIST, Context.MODE_PRIVATE);
        String seriObj = updateList.getString("JSON",null);
        if (seriObj != null){
            Gson g = new Gson();
            Type t = new TypeToken<LinkedList<journalEnt>>(){}.getType();
            LinkedList<journalEnt> newVals = g.fromJson(seriObj, t);
            entries = newVals;

        }
    }


    public void homeActivityOpen() {
        Intent intent = new Intent(this, homeActivity.class);
        startActivity(intent);
        finish();
    }
    public void entriesActivityOpen() {
        Intent intent = new Intent(this, entriesActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.journal_layout);

        mood = findViewById(R.id.feel);

        homeBtn = findViewById(R.id.homeBtnjournal);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeActivityOpen();
            }
        });

        entriesBtn = findViewById(R.id.viewEntsBtn);
        entriesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entriesActivityOpen();

            }
        });

        ImageView moodcloud1 = findViewById(R.id.moodcloud1);
        ImageView moodcloud2 = findViewById(R.id.moodcloud2);
        ImageView moodcloud3 = findViewById(R.id.moodcloud3);
        ImageView moodcloud4 = findViewById(R.id.moodcloud4);
        ImageView moodcloud5 = findViewById(R.id.moodcloud5);

        saveBtn = findViewById(R.id.entSaveBtn);

        moodcloud1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moodInt = 1;
                mood.setText("1 - really bad");
                imageVal = R.drawable.mood1;
            }
        });
        moodcloud2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moodInt = 2;
                mood.setText("2 - not too great");
                imageVal = R.drawable.mood2;

            }
        });
        moodcloud3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moodInt = 3;
                mood.setText("3 - alright");
                imageVal = R.drawable.mood3;

            }
        });
        moodcloud4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moodInt = 4;
                mood.setText("4 - pretty good");
                imageVal = R.drawable.mood4;

            }
        });
        moodcloud5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moodInt = 5;
                mood.setText("5 - amazing!");

                imageVal = R.drawable.mood5;

            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                feelTxt = findViewById(R.id.journalTxtBar);
                if (!(feelTxt.getText()).equals("")) {
                    feelings = feelTxt.getText().toString();
                }

                moodsList.add(moodInt);

                Calendar c = Calendar.getInstance();
                SimpleDateFormat datef = new SimpleDateFormat("dd/MM/yyyy");
                dateM = datef.format(c.getTime()).toString();

                feelTxt.setText("");
                Toast.makeText(getBaseContext(), "saved ♥", Toast.LENGTH_SHORT).show();

                journalEnt jk = new journalEnt(dateM, imageVal, feelings, moodInt);
                entries.add(jk);

                feelings = "";
                moodInt = 0;
                mood.setText("");

                saveEntry();

            }


        });

    }

}
package com.example.auraia.Journal.Entries;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.auraia.SETTINGS.settingsActivity;
import com.example.auraia.Journal.JournalsIn.journalEnt;
import com.example.myapplication.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.LinkedList;

public class entriesActivity extends AppCompatActivity {
    private Button homeBtn;

    private  Button done;
    private Button cancel;
    private EditText newJournalTxt;
    private TextView gone;
    private ImageView cloudSwap;
    int moodVal = 0;
    int posNew;
    int imgsource = R.drawable.mood0;
    String newTxt = "";

    private boolean clearCheck;
    public static final String ELIST = "ENTRIES_LIST";

    private RecyclerView entView;
    private adaptJournal adapter;
    private RecyclerView.LayoutManager layoutFr;

    private TextView date;
    private TextView entry;
    private ImageView img;
    private Button ok;
    private Button edit;

    LinkedList<journalEnt> entriesList = new LinkedList<>();
    LinkedList<journalEnt> eList = new LinkedList<>();

    public void journalActivityBack() {
        finish();
    }

    public void viewEntry(final journalEnt j) {
        setContentView(R.layout.viewentry_layout);
        date = findViewById(R.id.dateTxt);
        entry = findViewById(R.id.entryText);
        img = findViewById(R.id.cloudImg);
        ok = findViewById(R.id.okBtn);
        edit = findViewById(R.id.editBtn);

        moodVal = j.getMood();

        date.setText(j.getDate());
        entry.setText(j.getEntry());
        img.setImageResource(j.getImageResource());

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.entries_layout);
                reload();
            }
        });

        edit.setOnClickListener(new  View.OnClickListener(){

            @Override
            public void onClick(View v) {
                reJournal(j);
                //get the position of the journal
            }
        });

    }

    public void reJournal(final journalEnt j){
        done = findViewById(R.id.doneBtn);
        cancel = findViewById(R.id.cancelBtn);
        newJournalTxt= findViewById(R.id.editJournal);
        cloudSwap = findViewById(R.id.cloudImg);

        newTxt="";
        moodVal=0;
        imgsource=R.drawable.mood0;

        ok.setVisibility(View.INVISIBLE);
        edit.setVisibility(View.INVISIBLE);
        entry.setVisibility(View.INVISIBLE);

        done.setVisibility(View.VISIBLE);
        cancel.setVisibility(View.VISIBLE);
        newJournalTxt.setVisibility(View.VISIBLE);

        cloudSwap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(moodVal==0){
                    cloudSwap.setImageResource(R.drawable.mood1);
                    moodVal=1;
                    imgsource = R.drawable.mood1;
                }
                else{
                    if(moodVal==1){
                        cloudSwap.setImageResource(R.drawable.mood2);
                        moodVal=2;
                        imgsource = R.drawable.mood2;
                    }
                    else {
                        if(moodVal==2){
                            cloudSwap.setImageResource(R.drawable.mood3);
                            moodVal=3;
                            imgsource = R.drawable.mood3;
                        }
                        else{
                            if(moodVal==3){
                                cloudSwap.setImageResource(R.drawable.mood4);
                                moodVal=4;
                                imgsource = R.drawable.mood4;
                            }
                            else{
                                if(moodVal==4){
                                    cloudSwap.setImageResource(R.drawable.mood5);
                                    moodVal=5;
                                    imgsource = R.drawable.mood5;
                                }
                                else{
                                    if(moodVal==5){
                                    cloudSwap.setImageResource(R.drawable.mood0);
                                    moodVal=0;
                                    imgsource = R.drawable.mood0;
                                }}
                            }
                        }
                    }
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit();

                date.setText(j.getDate());
                entry.setText(j.getEntry());
                img.setImageResource(j.getImageResource());
                cloudSwap.setOnClickListener(null);

            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newTxt = newJournalTxt.getText().toString();
                journalEnt newJ= new journalEnt(j.getDate(),imgsource,newTxt,moodVal);

                date.setText(newJ.getDate());
                entry.setText(newJ.getEntry());
                img.setImageResource(newJ.getImageResource());
                cloudSwap.setOnClickListener(null);
                Toast.makeText(getBaseContext(), "saved ♥", Toast.LENGTH_SHORT).show();
                changeItem(posNew, newJ);
                exit();

            }
        });
    }

    public void exit(){
        ok.setVisibility(View.VISIBLE);
        edit.setVisibility(View.VISIBLE);
        entry.setVisibility(View.VISIBLE);

        done.setVisibility(View.INVISIBLE);
        cancel.setVisibility(View.INVISIBLE);
        newJournalTxt.setVisibility(View.INVISIBLE);
        newTxt="";
        moodVal=0;
        imgsource=R.drawable.mood0;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entries_layout);

        SharedPreferences updateList = this.getSharedPreferences(ELIST, Context.MODE_PRIVATE);
        String seriObj = updateList.getString("JSON",null);
        if (seriObj != null){
            Gson g = new Gson();
            Type t = new TypeToken<LinkedList<journalEnt>>(){}.getType();
            LinkedList<journalEnt> newVals = g.fromJson(seriObj, t);

            for (int i = 0; i<newVals.size(); i++){
                entriesList.add(newVals.get(i));
            }
        }


        //SharedPreferences clearEnts = getSharedPreferences(XENTS, MODE_PRIVATE);
        //clearCheck = clearEnts.getBoolean("DELETE", false);
        clearCheck = settingsActivity.entClear;

        if(clearCheck==true){
            entriesList.clear();
            clearCheck=false;
            settingsActivity.entClear=false;
        }
        reload();

    }

    public void reload(){
        final LinkedList<journalEnt> clickList = entriesList;

        entView = findViewById(R.id.entriesRecycle);
        layoutFr = new LinearLayoutManager(this);
        adapter = new adaptJournal(entriesList);//view the list...
        gone = findViewById(R.id.goneEmpty);
        if(entriesList.size()==0){
            gone.setVisibility(View.VISIBLE);
        }

        entView.setHasFixedSize(false);
        entView.setLayoutManager(layoutFr);
        entView.setAdapter(adapter);

        adapter.setOnJournalClickListener(new adaptJournal.OnJournalClickListener() {
            @Override
            public void onJournalClick(int position) {
                journalEnt jReal = clickList.get(position);
                posNew = position;
                viewEntry(jReal);

            }
        });

        homeBtn = findViewById(R.id.backtoJournal);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                journalActivityBack();
            }
        });
    }

    public void changeItem(int position, journalEnt k){
        entriesList.get(position).setEntry(k.getEntry());
        entriesList.get(position).setMood(k.getMood());
        entriesList.get(position).setsubText(k);
        entriesList.get(position).setImgResource(k.getImageResource());
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences delPrefs = this.getSharedPreferences("ENTRIES_DELETE", MODE_PRIVATE);
    }



        @Override
    public void onStop(){
        super.onStop();
        SharedPreferences updateList = this.getSharedPreferences(ELIST, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = updateList.edit();
        e.putString("JSON",setList(entriesList));
        e.commit();


    }

    public <journalEnt> String setList(LinkedList<journalEnt> list){
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }


}
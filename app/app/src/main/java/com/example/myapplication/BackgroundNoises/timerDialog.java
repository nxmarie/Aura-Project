package com.example.myapplication.BackgroundNoises;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

public class timerDialog extends AppCompatDialogFragment {
    private Button mins10;
    private Button mins30;
    private Button mins45;
    private Button mins60;
    private Button mins120;
    private DialogListen listener;
    int timer = 0;
    String timeA = "";
    boolean selected = false;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.timer_layout, null);
        builder.setView(view)
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (selected==true){
                    listener.applyTexts(timer, timeA);
                }

            }
        });

        mins10 = view.findViewById(R.id.t10);
        mins30 = view.findViewById(R.id.t30);
        mins45 = view.findViewById(R.id.t45);
        mins60 = view.findViewById(R.id.t60);
        mins120 = view.findViewById(R.id.t120);

        mins10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer = 10;
                timeA = "10 minutes!";
                selected = true;
            }
        });

        mins30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer = 30;
                timeA = "30 minutes!";
                selected = true;

            }
        });

        mins45.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer = 45;
                timeA = "45 minutes!";
                selected = true;

            }});

        mins60.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer = 60;
                timeA = "1 hour";
                selected = true;

            }
        });

        mins120.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer = 120;
                timeA = "2 hours!";
                selected = true;

            }
        });
          return builder.create();

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (DialogListen) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "error!");
        }
    }
    public interface DialogListen {
        void applyTexts(int t, String textA);
    }

}
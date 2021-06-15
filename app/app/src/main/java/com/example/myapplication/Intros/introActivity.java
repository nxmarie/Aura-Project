package com.example.myapplication.Intros;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AppOpsManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;
import com.google.gson.Gson;

import java.util.*;

public class introActivity extends AppCompatActivity {

    LinkedList<Double> sleepTimes = new LinkedList<>();
    public static final String SLEEPLIST = "night night";



    private Button btn;
    private TextView msg;

    public void homeActivityOpen() {
        Intent intent = new Intent(this, homeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (!Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            startActivity(intent);
        }
        //Uri.parse("package:" + getPackageName();
        NotificationManager notificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        if (!notificationManager.isNotificationPolicyAccessGranted()) {
            Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
            startActivity(intent);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_layout);

        msg = findViewById(R.id.welcomeMsg);
        msg.setMovementMethod(new ScrollingMovementMethod());
        btn = findViewById(R.id.nxtBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeActivityOpen();
            }
        });

    }

    public <Double> String setListDouble(LinkedList<Double> list){
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences schleep = this.getSharedPreferences(SLEEPLIST,Context.MODE_PRIVATE);
        SharedPreferences.Editor e = schleep.edit();
        e.putString("STATSS",setListDouble(sleepTimes));
        e.commit();
    }

    private boolean isAccessGranted() {
        try {
            PackageManager packageManager = getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(getPackageName(), 0);
            AppOpsManager appOpsManager = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
            int mode = 0;
            if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.KITKAT) {
                mode = appOpsManager.unsafeCheckOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                        applicationInfo.uid, applicationInfo.packageName);

            }
            return (mode == AppOpsManager.MODE_ALLOWED);

        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

}
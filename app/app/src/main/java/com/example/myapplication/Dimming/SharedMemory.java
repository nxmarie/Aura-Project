package com.example.myapplication.Dimming;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

public class SharedMemory {
    private SharedPreferences mSharedPreferences;

    public SharedMemory(Context context){
        mSharedPreferences = context.getSharedPreferences("SCREEN_FILTER_PREF", Context.MODE_PRIVATE);
    }

    private int getValue(String prop, int def){
        return mSharedPreferences.getInt(prop,def);
    }
    public int getDark(){
        int r = getValue("redtxt", 0x00);
        return r;
    }

    public int getGreen(){
        int g = 181 - getDark();
        if (g<=0){
            g = 0;
        }
        return g;
    }

    public int getRed(){
        int r = 238 - getDark() ;
        if (r<=0){
            r = 0;
        }
        return r;
    }


    public int getAlpha(){
        int a = getValue("alphatxt", 0x00)+30;
        if(a>200){
           a = 200;
        }
        return a;
    }

    private void setValue(String value, int v){
        mSharedPreferences.edit().putInt(value, v).apply();
    }

    public void setAlpha(int val) {
        setValue("alphatxt", val);
    }
    public void setRed(int val) {
        setValue("redtxt", val);
    }


    public int getColour() {
        return Color.argb(getAlpha(),getRed(),getGreen(),0);

    }
}

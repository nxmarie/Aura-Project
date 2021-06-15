package com.example.myapplication.Journal.JournalsIn;

import android.content.Intent;

import androidx.cardview.widget.CardView;

import com.example.myapplication.Intros.homeActivity;

public class iCardView {

    private int mImageResource;
    private String dateTxt;

    public iCardView(int imageResource, String date)
    {
        this.mImageResource = imageResource;
        this.dateTxt = date;
    }

    public int getImageResource() {
        return mImageResource;
    }
    public String getDate() {
        return dateTxt;
    }

    public void setImgResource(int img){
        mImageResource = img; }


}

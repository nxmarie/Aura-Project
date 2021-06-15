package com.example.auraia.Journal.JournalsIn;

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

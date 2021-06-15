package com.example.auraia.Journal.JournalsIn;

public class journalEnt extends iCardView{

    private int mood;
    private String entry;
    private String firstCharstxt;

    public journalEnt(String date, int imgSource, String entry, int mood) {
        super( imgSource, date);

        this.entry = entry;
        this.mood = mood;
        this.firstCharstxt = entry.substring(0,check25(entry))+"...";
    }

    int k = 25;

    public int check25(String j){
        if (j.length()<25){
            k = entry.length();
        }
        else {
            k=25;
        }
        return k;
    }

    //the getters

    public String getEntry(){
        return entry;
    }

    public int getMood(){
        return mood;
    }

    public String getsubText() {
        return firstCharstxt;
    }


    //the setters

    public void setMood(int newMood){
        mood = newMood;
    }

    public void setsubText(journalEnt j) {
        String e = j.getEntry();
        firstCharstxt = (e.substring(0,check25(e))+"...");
    }
    public void setEntry(String EntryInput){
        entry = EntryInput;
    }



}

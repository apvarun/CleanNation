package com.cleannation.app;

/**
 * Created by varun on 15/12/15.
 */

import com.parse.ParseFile;
import com.parse.ParseObject;

import java.util.List;

public class SpotData {

    String mText1;

    private ParseObject spot;

    List<ParseObject> myList;

    public SpotData (String text){//List<ParseObject> list){
        //myList=list;
        //mText1=text.getObjectId();
        mText1=text;
    }
    public SpotData(ParseObject data){
        spot=data;
    }
    public String getLatitude(){
        return spot.getNumber("latitude").toString();
    }
    public String getLongitude(){
        return spot.getNumber("longitude").toString();
    }
    public ParseFile getImage(){
        return spot.getParseFile("image");
    }
    public String getUser(){
        return spot.getString("username");
    }
    public String getDateTime(){
        return spot.getCreatedAt().toString();
    }
    public String getID(){
        return spot.getObjectId();
    }

    public String getComment(){
        return spot.getString("comment");
    }


    public String getmText1() {
        return mText1;
    }
    public void setmText1(String mText1) {
        this.mText1 = mText1;
    }
}

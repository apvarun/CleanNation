package com.cleannation.app;

import com.parse.ParseObject;

import java.util.List;

/**
 * Created by varun on 17/12/15.
 */
public class MeetupData {

    private ParseObject spot;

    List<ParseObject> myList;

    public MeetupData (String text){//List<ParseObject> list){
        //myList=list;
        //mText1=text.getObjectId();
        //mText1=text;
    }
    public MeetupData(ParseObject data){
        spot=data;
    }
    public String getName(){
        return spot.getString("name");
    }
    public String getLocation(){
        return spot.getString("place");
    }
    public String getDateTime(){
        return spot.getCreatedAt().toString();
    }
    public String getID(){
        return spot.getObjectId();
    }
    public String getDate(){
        return spot.getString("date");
    }

    public String getPhone(){
        return spot.getString("phone");
    }
    public String getPin(){
        return spot.getString("pincode");
    }
}

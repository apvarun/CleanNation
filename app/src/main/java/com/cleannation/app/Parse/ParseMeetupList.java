package com.cleannation.app.Parse;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.StrictMode;
import android.util.Log;

import com.cleannation.app.MeetupData;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by varun on 17/12/15.
 */
public class ParseMeetupList {

    public static ArrayList<MeetupData> meetupList;

    public ParseMeetupList() {
    }

    //ProgressDialog dialog;

    @SuppressLint("NewApi")
    public ParseMeetupList(Activity activity){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


    }

    public ArrayList<MeetupData> getArrayList(){
        return meetupList;
    }


    public void getList(Activity activity) throws Exception {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("meetups");
        ArrayList<MeetupData> list=new ArrayList<MeetupData>();
        Log.d("cleanNation", "in");

//        if(ParseUser.getCurrentUser() == null) {
//            throw new Exception("Please log in");
//        }
        //query.whereEqualTo("User", ParseUser.getCurrentUser());

        try {
            List<ParseObject> sList = query.find();
            Log.d("cleanNation","in2");
            if(sList==null || sList.isEmpty()) {
                throw new Exception("You don not have any meetups");
            } else {
                Iterator<ParseObject> iter = sList.iterator();
                int index=0;
                Log.d("cleanNation",iter.toString());
                while(iter.hasNext()) {
                    //spotList.add(new SpotData(iter.next()));
                    MeetupData obj = new MeetupData(iter.next());
                    list.add(index, obj);
                    Log.d("cleanNation",obj.toString());
                    try {
                        Log.d("cleanNation", obj.getLocation());
                    }catch(Exception e){
                        Log.d("cleanNation", e.toString());
                    }
                    index++;
                }
            }
        } catch (ParseException e) {
            throw new Exception("Could retrieve Doctor list");
        }
        meetupList=list;

        Log.d("cleanNation",meetupList.toString());

        return;
    }

    public String getItemOf(int position){
        return meetupList.get(position).getID();
    }
}

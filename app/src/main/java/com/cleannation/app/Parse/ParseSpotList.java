package com.cleannation.app.Parse;

/**
 * Created by varun on 15/12/15.
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.StrictMode;
import android.util.Log;

import com.cleannation.app.SpotData;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ParseSpotList{

    public static ArrayList<SpotData> spotList;

    public ParseSpotList() {
    }

    //ProgressDialog dialog;

    @SuppressLint("NewApi")
    public ParseSpotList(Activity activity){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


    }

    public ArrayList<SpotData> getArrayList(){
        return spotList;
    }


//    @SuppressLint("NewApi")
//    @Override
//    public void query(String specialization, Activity activity, HashMap<String, String> advSearch) throws Exception {
//        ParseQuery<ParseObject> query;
//
//        List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();
//
//		/* If there was a specialization put it in the query list */
//        if(specialization != null) {
//            query = ParseQuery.getQuery(PConstants.PARSE_DOCTOR);
//            query.whereEqualTo(PConstants.PARSE_SPECIALIZATION, specialization);
//            queries.add(query);
//        }
//
//		/* If there were advanced search terms add them to the query list */
//        if(advSearch != null) {
//            Iterator<String> iter = advSearch.keySet().iterator();
//            while(iter.hasNext()) {
//                String key = iter.next();
//                String value = advSearch.get(key);
//                if(!value.isEmpty()) {
//                    query = ParseQuery.getQuery(PConstants.PARSE_RATING);
//                    query.whereEqualTo("doctorID", value);
//                    queries.add(query);
//                }
//            }
//        }
//
//		/* Search for all of the queries in the query list */
//        query = ParseQuery.or(queries);
//        ArrayList<DoctorInterface> Doctorlist = new ArrayList<DoctorInterface>();
//        try {
//			/* Retrieve the search results and convert them from parseobjects to Doctor objects */
//            List<ParseObject> list = query.find();
//            Iterator<ParseObject> iter = list.iterator();
//            while(iter.hasNext()) {
//                Doctorlist.add(new ParseDoctor(iter.next()));
//            }
//
//			/* Set the current Doctor list to the search results */
//            currentDoctorList = Doctorlist;
//        } catch (ParseException e) {
//            throw new WebServiceException("Search Failed");
//        }
//    }


    public void getList(Activity activity) throws Exception {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("spots");
        ArrayList<SpotData> list=new ArrayList<SpotData>();
        Log.d("cleanNation","in");
//        if(ParseUser.getCurrentUser() == null) {
//            throw new Exception("Please log in");
//        }
        //query.whereEqualTo("User", ParseUser.getCurrentUser());
        try {
            List<ParseObject> sList = query.find();
            Log.d("cleanNation","in2");
            if(sList==null || sList.isEmpty()) {
                throw new Exception("You don not have any spots");
            } else {
                Iterator<ParseObject> iter = sList.iterator();
                int index=0;
                Log.d("cleanNation",iter.toString());
                while(iter.hasNext()) {
                    //spotList.add(new SpotData(iter.next()));
                    SpotData obj = new SpotData(iter.next());
                    list.add(index, obj);
                    Log.d("cleanNation",obj.toString());
                    Log.d("cleanNation",obj.getImage().toString());
                    index++;
                }
            }
        } catch (ParseException e) {
            throw new Exception("Could retrieve Doctor list");
        }
        spotList=list;
        Log.d("cleanNation",spotList.toString());
        return;
    }

    public String getItemOf(int position){
        return spotList.get(position).getID();
    }
}


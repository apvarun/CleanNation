package com.cleannation.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.cleannation.app.Parse.ParseMeetupList;
import com.cleannation.app.Util.DividerItemDecoration;
import com.cleannation.app.adapter.MyRecyclerViewAdapter2;

import java.util.ArrayList;

public class Meetups extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "RecyclerViewActivity";

    ProgressDialog dialog;

    ParseMeetupList p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetups_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_meetups);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view_meetup);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        dialog=new ProgressDialog(this);
        dialog.setMessage("Listing all the meetups");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);


        mAdapter = new MyRecyclerViewAdapter2(getDataSet());
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);

        AsyncTaskRunner runner = new AsyncTaskRunner(this);
        String sleepTime = "1000";
        dialog.show();
        runner.execute(sleepTime);


    }

    @Override
    protected void onResume() {
        super.onResume();
        ((MyRecyclerViewAdapter2) mAdapter).setOnItemClickListener(new
        MyRecyclerViewAdapter2.MyClickListener() {
        @Override
        public void onItemClick(int position, View v) {
//        Log.i(LOG_TAG, " Clicked on Item " + position);
//        Intent i=new Intent(Meetups.this,SpotContent.class);
//        i.putExtra("Id",p.getItemOf(position));
//        //i.putExtra("id",this.getItem(position));
//
//
//        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
//        // the context of the activity
//        Meetups.this,
//
//        // For each shared element, add to this method a new Pair item,
//        // which contains the reference of the view we are transitioning *from*,
//        // and the value of the transitionName attribute
//        new Pair<View, String>(v.findViewById(R.id.spot_image),
//            getString(R.string.transition_name_circle)),
//        new Pair<View, String>(v.findViewById(R.id.textView2),
//            getString(R.string.transition_name_name))
//                        new Pair<View, String>(view.findViewById(R.id.CONTACT_phone),
//                                getString(R.string.transition_name_phone))
//        );
//
//        //ActivityCompat.startActivity(Meetups.this, i, options.toBundle());
//
//        //startActivity(i);
            };
        });
    }


    private ArrayList<MeetupData> getDataSet() {
        ArrayList results = new ArrayList<MeetupData>();
        for (int index = 0; index < 0; index++) {
            MeetupData obj = new MeetupData("No data available");
            results.add(index, obj);
        }
        return results;
    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        Activity act;

        public AsyncTaskRunner(Activity a){
            act=a;
        }

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()
            try {
                // Do your long operations here and return the result
                int time = Integer.parseInt(params[0]);
                // Sleeping for given time period
                p=new ParseMeetupList();
                try {
                    Log.d("cleanNation","start");
                    p.getList(act);
                    mAdapter = new MyRecyclerViewAdapter2(p.getArrayList());

                }catch(Exception e){
                    mAdapter = new MyRecyclerViewAdapter2(getDataSet());
                }

                Log.d("cleanNation","started");

            }catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }

        @Override
        protected void onPostExecute(String result) {

            mRecyclerView.setAdapter(mAdapter);
            dialog.dismiss();

        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(String... text) {

        }
    }

}

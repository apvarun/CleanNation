package com.cleannation.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class SpotContent extends AppCompatActivity {

    ImageView img;
    SpotData obj=null;
    ParseFile image;

    String lat="";
    String lon="";

    TextView comments,dateTime;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spot_content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Intent in=new Intent(Intent.ACTION_VIEW, Uri.parse("geo:"+lat+","+lon+"?q="+lat+","+lon+"(Spot)"));
                startActivity(in);
            }
        });

        toolbar.setTitle("");

        img=(ImageView)findViewById(R.id.DETAILS_circle);
        comments=(TextView)findViewById(R.id.txtComments);
        dateTime=(TextView)findViewById(R.id.txtUploadedDate);

        Intent i=getIntent();
        String id=i.getStringExtra("Id");


        AsyncTaskRunner runner = new AsyncTaskRunner(this);
        runner.execute(id);

        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setTitle(getResources().getString(R.string.app_name));
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        final TextView appText=(TextView)findViewById(R.id.textbar);


        //collapsingToolbarLayout.setContentScrimColor(Color.BLUE);
        //collapsingToolbarLayout.setStatusBarScrimColor(Color.GREEN);
        //
        collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.myPrimaryColor));
        collapsingToolbarLayout.setStatusBarScrimColor(getResources().getColor(R.color.myPrimaryDarkColor));

        dialog=new ProgressDialog(this);
        dialog.setMessage("Gathering spot information");
        //dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();



    }


    private class AsyncTaskRunner extends AsyncTask<String, String, String>  {

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

                // Sleeping for given time period

                ParseQuery<ParseObject> query = ParseQuery.getQuery("spots");
                //query.whereContains("ObjectId",params[0]);

                Log.d("cleanNation", params[0]);

//                List<ParseObject> out=query.find();
//
//
//
//                if(out==null || out.isEmpty()) {
//                    throw new Exception("You don not have any spots");
//                } else {
//                    Iterator<ParseObject> iter = out.iterator();
//                    int index=0;
//                    Log.d("cleanNation",iter.toString());
//                    if(iter.hasNext()) {
//                        //spotList.add(new SpotData(iter.next()));
//                        obj = new SpotData(iter.next());
//
//                    }
//                }

                try {
                    obj = new SpotData(query.get(params[0]));
                }catch(Exception e) {

                }




            }catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }

        @Override
        protected void onPostExecute(String result) {

            try {
                image = obj.getImage();
                comments.setText(obj.getComment());
                dateTime.setText(obj.getDateTime());
                lat=obj.getLatitude();
                lon=obj.getLongitude();
                Log.d("CleanNation",lat+","+lon);
                loadImage(image, img);

            }
            catch(Exception e){

            }
            dialog.dismiss();
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(String... text) {

        }
    }


    private void loadImage(ParseFile thumbnail, final ImageView img){
        Log.d("cleanNationThumb",thumbnail.toString());
        Log.d("cleanNationThumb",Boolean.toString(thumbnail.isDataAvailable()));
        //if(thumbnail.isDataAvailable()){
        try{
            thumbnail.getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    Bitmap bmp= BitmapFactory.decodeByteArray(data, 0, data.length);
                    img.setImageBitmap(bmp);
                }
            });
        }
        catch(Exception e){
            //else{
            img.setImageResource(R.drawable.wallpaper);
        }
    }
}

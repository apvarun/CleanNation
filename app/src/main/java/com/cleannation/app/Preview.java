package com.cleannation.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;

public class Preview extends Activity {

    // LogCat tag
    private static final String TAG ="CleanNation";
    LocationManager myLocationManager;
    // private ProgressBar progressBar;
    private String filePath = null;
    // private TextView txtPercentage;
    private ImageView imgPreview;

    private Button btncnf;
    Double lat=0.0;
    Double lon=0.0;

    EditText comment;


    static float deg=90;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        myLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        imgPreview = (ImageView) findViewById(R.id.imgPreview);
        btncnf = (Button) findViewById(R.id.cnfrm);
        comment=(EditText)findViewById(R.id.editText2);
        Intent i = getIntent();
        filePath = i.getStringExtra("filePath");
        boolean isImage = i.getBooleanExtra("isImage", true);
        if (filePath != null) {
            // Displaying the image or video on the screen
            previewMedia(isImage);
        } else {
            Toast.makeText(getApplicationContext(),
                    "Sorry, file path is missing!", Toast.LENGTH_LONG).show();
        }


        imgPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(deg==360f)
                    deg=0;
                imgPreview.setRotation(deg);
                deg+=90;
            }
        });

        final LocationListener listen = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

               try {
                    lat=location.getLatitude();
                    lon=location.getLongitude();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }
            @Override
            public void onProviderEnabled(String provider) {
            }
            @Override
            public void onProviderDisabled(String provider) {
            }
        };

        

        btncnf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(lat<=0.0 && lon<=0.0)
                    Toast.makeText(v.getContext(),"Please try again as we pull in the location details",Toast.LENGTH_SHORT).show();
                else{

                    final ProgressDialog dialog=new ProgressDialog(v.getContext());
                    dialog.setMessage("Uploading data");
                    //dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    dialog.setIndeterminate(true);
                    dialog.setCancelable(false);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();

                    BitmapFactory.Options options = new BitmapFactory.Options();

                    // down sizing image as it throws OutOfMemory Exception for larger
                    // images
                    options.inSampleSize = 8;
                    // Locate the image in res > drawable-hdpi
                    Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
                    // Convert it to byte
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    // Compress image to lower quality scale 1 - 100
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] image = stream.toByteArray();

                    // Create the ParseFile
                    final ParseFile file = new ParseFile("image", image);
                    // Upload the image into Parse Cloud
                    file.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            // Create a New Class called "ImageUpload" in Parse
                            final ParseObject query = new ParseObject("spots");

                            query.put("image", file);

                            Log.d("CleanNation", file.getUrl().toString());
                            Log.d("CleanNation",ParseUser.getCurrentUser().toString());

                            Log.d("CleanNation", Double.toString(lat));
                            Log.d("CleanNation", Double.toString(lon));

                            query.put("latitude", lat);
                            query.put("longitude", lon);
                            query.put("username", ParseUser.getCurrentUser());
                            query.put("comment", comment.getText().toString());

                            // Create the class and the columns
                            query.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {

                                    Log.d("CleanNation", query.getCreatedAt().toString());
                                    dialog.dismiss();
                                    Intent i = new Intent(v.getContext(), success.class);
                                    startActivity(i);
                                    finish();
                                }
                            });
                        }
                    });

                }

            }
        });

        Handler delayHandler = new Handler();// To create a delay
        Runnable r = new Runnable() {
            @Override
            public void run() {
                // Call this method after 1000 milliseconds
                //myLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listen);//gps provider,0secs,0distance moved,loc lis obj
//                if (checkSelfPermission(ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                    myLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,listen);
//                }
                myLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,listen);
                myLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,listen);
                myLocationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER,0,0,listen);

            }

        };
        delayHandler.postDelayed(r, 1000);}

        /**
         * Displaying captured image/video on the screen
         * */

        private void previewMedia(boolean isImage) {
        // Checking whether captured media is image or video
        if (isImage) {
            imgPreview.setVisibility(View.VISIBLE);
            //vidPreview.setVisibility(View.GONE);
            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();

            // down sizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;

            final Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);

            imgPreview.setImageBitmap(bitmap);
        } else {
            imgPreview.setVisibility(View.GONE);

        }


    }


}
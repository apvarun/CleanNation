package com.cleannation.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.parse.ParseUser;

import java.util.ArrayList;

import za.co.riggaroo.materialhelptutorial.TutorialItem;
import za.co.riggaroo.materialhelptutorial.tutorial.MaterialTutorialActivity;

public class Splash extends Activity {

    private static final int REQUEST_CODE = 1234;

    private static String TAG = Splash.class.getName();
    private static long SLEEP_TIME = 1;    // Sleep for some time

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);    // Removes title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);    // Removes notification bar

        setContentView(R.layout.activity_splash);

        // Start timer and launch main activity
        IntentLauncher launcher = new IntentLauncher();
        launcher.start();


    }

    private class IntentLauncher extends Thread {
        @Override
        /**
         * Sleep for some time and than start new activity.
         */
        public void run() {
            try {
                // Sleeping
                Thread.sleep(SLEEP_TIME*1000);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }

            Intent intent = new Intent(Splash.this, Home.class);

            // Start main activity
            myPrefs p=new myPrefs(getApplicationContext());
            if(!p.getStat())
                loadTutorial();
            else{
                try{
                    if(!ParseUser.getCurrentUser().isDataAvailable())
                        intent = new Intent(Splash.this, Login.class);
                }catch(Exception e){
                    intent = new Intent(Splash.this, Login.class);
                }
//                if(!ParseUser.getCurrentUser().isDataAvailable())
//                    intent = new Intent(Splash.this, Login.class);

                startActivity(intent);
                finish();
            }

        }
    }

    public void loadTutorial() {
        Intent mainAct = new Intent(this, MaterialTutorialActivity.class);
        mainAct.putParcelableArrayListExtra(MaterialTutorialActivity.MATERIAL_TUTORIAL_ARG_TUTORIAL_ITEMS, getTutorialItems(this));
        startActivityForResult(mainAct, REQUEST_CODE);

    }

    private ArrayList<TutorialItem> getTutorialItems(Context context) {
        TutorialItem tutorialItem1 = new TutorialItem(R.string.tutorial_screen1, R.string.tutorial_screen1_lite,
                R.color.slide_1, R.drawable.report,  R.drawable.report);

        TutorialItem tutorialItem2 = new TutorialItem(R.string.tutorial_screen2, R.string.tutorial_screen2_lite,
                R.color.slide_2,  R.drawable.look_around,  R.drawable.look_around);


        TutorialItem tutorialItem3 = new TutorialItem(R.string.tutorial_screen3, R.string.tutorial_screen3_lite,
                R.color.slide_3,  R.drawable.rescue, R.drawable.rescue);

        ArrayList<TutorialItem> tutorialItems = new ArrayList<>();
        tutorialItems.add(tutorialItem1);
        tutorialItems.add(tutorialItem2);
        tutorialItems.add(tutorialItem3);

        return tutorialItems;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //    super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE){

            //Toast.makeText(this, "Tutorial finished", Toast.LENGTH_LONG).show();

//            myPrefs p=new myPrefs(getApplicationContext());

            Intent intent;

//            if(p.getUser().length()<=0)
//                intent = new Intent(Splash.this, Login.class);
//            else {
//                intent = new Intent(Splash.this, Home.class);
//            }

            intent = new Intent(Splash.this, Home.class);

            try{
                if(!ParseUser.getCurrentUser().isDataAvailable())
                    intent = new Intent(Splash.this, Login.class);
            }catch(Exception e) {
                intent = new Intent(Splash.this, Login.class);
            }
            Splash.this.startActivity(intent);
            Splash.this.finish();

        }
    }
}
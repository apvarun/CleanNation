package com.cleannation.app;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.util.Calendar;

public class NewMeetup extends AppCompatActivity {

    EditText name, number, location, pincode;
    CheckBox cleaning, awareness;
    Button create;

    EditText dateView;
    private Calendar cal;

    int year,month,day;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_meetup);


        name = (EditText) findViewById(R.id.editText);
        number = (EditText) findViewById(R.id.editText3);
        location = (EditText) findViewById(R.id.editText4);
        pincode = (EditText) findViewById(R.id.editText5);


        cleaning = (CheckBox) findViewById(R.id.checkbox);
        awareness = (CheckBox) findViewById(R.id.checkbox2);

        create = (Button) findViewById(R.id.button);

        dateView = (EditText) findViewById(R.id.editText6);



        cal = Calendar.getInstance();

        year=cal.get(Calendar.YEAR);
        month=cal.get(Calendar.MONTH);
        day=cal.get(Calendar.DAY_OF_MONTH);

        //showDate(year, month, day);


        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Uploading data");
        //dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ParseObject query = new ParseObject("meetups");

                query.put("name", name.getText().toString());
                dialog.show();

                Log.d("CleanNation", name.getText().toString());
                Log.d("CleanNation", location.getText().toString());

                query.put("phone", number.getText().toString());
                query.put("place", location.getText().toString());
                query.put("pincode", pincode.getText().toString());
                query.put("date", dateView.getText().toString());

                Boolean b = cleaning.isChecked();
                query.put("cleaning", b);
                b = awareness.isChecked();
                query.put("awareness", b);

                // Create the class and the columns
                query.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {

                        //Log.d("CleanNation", query.getCreatedAt().toString());
                        dialog.dismiss();
                        Toast.makeText(v.getContext(), "Meet Up created", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });

    }





}

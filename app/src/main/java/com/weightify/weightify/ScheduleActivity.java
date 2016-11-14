package com.weightify.weightify;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class ScheduleActivity extends AppCompatActivity {

    TextView tvBF, tvSnack1, tvSnack2, tvSnack3, tvDinner, tvLunch;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        DataBaseHelper myDbHelper = new DataBaseHelper(getApplicationContext());

        myDbHelper = new DataBaseHelper(this);

       //Toast.makeText(ScheduleActivity.this, myDbHelper.getDBData("lunch"), Toast.LENGTH_SHORT).show();

//
        tvBF = (TextView) findViewById(R.id.tvBF);
        tvSnack1 = (TextView) findViewById(R.id.tvSnack1);
        tvSnack2 = (TextView) findViewById(R.id.tvSnack2);
        tvSnack3 = (TextView) findViewById(R.id.tvSnack3);
        tvLunch = (TextView) findViewById(R.id.tvLunch);
        tvDinner = (TextView) findViewById(R.id.tvDinner);
//

//
//        tvBF.setText(myDbHelper.getDBData("breakfast"));
//        tvSnack1.setText(myDbHelper.getDBData("snack1"));
//        tvSnack2.setText(myDbHelper.getDBData("snack2"));
//        tvSnack3.setText(myDbHelper.getDBData("snack3"));
//        tvLunch.setText(myDbHelper.getDBData("lunch"));
//        tvDinner.setText(myDbHelper.getDBData("dinner"));

        SharedPreferences settings;
        settings = getSharedPreferences("weightify_prefs", Context.MODE_PRIVATE); //1
        tvBF.setText(settings.getString("breakfast", "8:00am"));
        tvSnack1.setText(settings.getString("snack1", "10:15am"));
        tvSnack2.setText(settings.getString("snack2", "3:45pm"));
        tvSnack3.setText(settings.getString("snack3", "8:30pm"));
        tvLunch.setText(settings.getString("lunch", "12:30pm"));
        tvDinner.setText(settings.getString("dinner", "7:00pm"));


    }



    //getDBData("dinner");


}

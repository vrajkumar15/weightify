package com.weightify.weightify;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void resetValues(View v){

        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = getSharedPreferences("weightify_prefs", Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2

        editor.putString("breakfast", "8:00am"); //3
        editor.putString("snack1", "10:15am");
        editor.putString("snack2", "3:45pm");
        editor.putString("snack3", "8:30pm");
        editor.putString("lunch", "12:30pm");
        editor.putString("dinner", "7:00pm");
        editor.commit(); //4


//
//        tvBF = (TextView) findViewById(R.id.tvBF);
//        tvSnack1 = (TextView) findViewById(R.id.tvSnack1);
//        tvSnack2 = (TextView) findViewById(R.id.tvSnack2);
//        tvSnack3 = (TextView) findViewById(R.id.tvSnack3);
//        tvLunch = (TextView) findViewById(R.id.tvLunch);
//        tvDinner = (TextView) findViewById(R.id.tvDinner);
//
            //Toast.makeText(ScheduleActivity.this, myDbHelper.getDBData("lunch"), Toast.LENGTH_SHORT).show();

//        tvBF.setText("8:00am");
//        tvSnack1.setText("10:16am");
//        tvSnack2.setText("3:46pm");
//        tvSnack3.setText("8:30pm");
//        tvLunch.setText("12:30pm");
//        tvDinner.setText("8:30pm");




    }


}

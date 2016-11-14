package com.weightify.weightify;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

//import com.pushtorefresh.storio.sqlite.queries.Query;

import java.io.IOException;

public class HomeActivity extends AppCompatActivity {
    //public static final String PREFS_NAME = "weightify_prefs";
    //SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        DataBaseHelper myDbHelper = new DataBaseHelper(getApplicationContext());

        myDbHelper = new DataBaseHelper(this);


        if (!myDbHelper.checkDataBase()) {
            try {
                Toast.makeText(HomeActivity.this, "DB Doesnt Exist", Toast.LENGTH_SHORT).show();
                myDbHelper.createDataBase();
                initiatePrefValues();

            } catch (IOException ioe) {

                throw new Error("Unable to create database");

            }

        } else {
            Toast.makeText(HomeActivity.this, "DB Exists", Toast.LENGTH_SHORT).show();
        }

        try {

            SQLiteDatabase db = myDbHelper.openDataBase();
            String s;
            Cursor c = db.rawQuery("Select * from mealschedule", null);
            c.moveToFirst();
            s=c.getString(c.getColumnIndex("lunch"));
            db.close();
            Toast.makeText(HomeActivity.this, s, Toast.LENGTH_SHORT).show();

        }catch(SQLiteException sqle){

            throw sqle;

        }

        getDBData("dinner");

    }

    public void initiatePrefValues() {
        SharedPreferences settings;
        Editor editor;
        settings = getSharedPreferences("weightify_prefs", Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2

        editor.putString("breakfast", "8:00am"); //3
        editor.putString("snack1", "10:15am");
        editor.putString("snack2", "3:45pm");
        editor.putString("snack3", "8:30pm");
        editor.putString("lunch", "12:30pm");
        editor.putString("dinner", "7:00pm");
        editor.commit(); //4
    }


    public String getDBData(String meal){
        try {

            DataBaseHelper myDbHelper = new DataBaseHelper(getApplicationContext());
            myDbHelper = new DataBaseHelper(this);
            SQLiteDatabase db = myDbHelper.openDataBase();
            String s;
            Cursor c = db.rawQuery("Select * from mealschedule", null);
            c.moveToFirst();
            s=c.getString(c.getColumnIndex(meal));
            db.close();
            Toast.makeText(HomeActivity.this, s, Toast.LENGTH_SHORT).show();
            return s;

        }catch(SQLiteException sqle){

            throw sqle;

        }
    }

    public void showSchedule(View v) {
        Intent i = new Intent(this, ScheduleActivity.class);
        this.startActivity(i);
    }
    public void addTime(View v) {
        Intent i = new Intent(this, AddTimeActivity.class);
        this.startActivity(i);
    }
    public void showSettings(View v) {
        Intent i = new Intent(this, SettingsActivity.class);
        this.startActivity(i);
    }
}


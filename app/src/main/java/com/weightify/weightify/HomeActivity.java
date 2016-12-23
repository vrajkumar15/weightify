package com.weightify.weightify;

import android.content.Context;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.Calendar;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Date;

//import com.pushtorefresh.storio.sqlite.queries.Query;
import com.weightify.weightify.ScheduleClient;

import java.io.IOException;

public class HomeActivity extends Activity {
    //public static final String PREFS_NAME = "weightify_prefs";
    //SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    private ScheduleClient scheduleClient1;
    private ScheduleClient scheduleClient2;
    private ScheduleClient scheduleClient3;

    //private DatePicker picker;


    TextView tvDay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        scheduleClient1 = new ScheduleClient(this);
        scheduleClient1.doBindService();
        scheduleClient2 = new ScheduleClient(this);
        scheduleClient2.doBindService();
        scheduleClient3 = new ScheduleClient(this);
        scheduleClient3.doBindService();

        DataBaseHelper myDbHelper = new DataBaseHelper(getApplicationContext());

        myDbHelper = new DataBaseHelper(this);

        tvDay = (TextView) findViewById(R.id.tvDay);


        if (!myDbHelper.checkDataBase()) {
            try {
                Toast.makeText(HomeActivity.this, "DB Doesnt Exist", Toast.LENGTH_SHORT).show();
                myDbHelper.createDataBase();
                initiatePrefValues();

            } catch (IOException ioe) {

                throw new Error("Unable to create database");

            }

        } else {
            SharedPreferences settings;
            settings = getSharedPreferences("weightify_prefs", Context.MODE_PRIVATE); //1
            tvDay.setText("Day " + settings.getString("rowcount", "00"));


            Toast.makeText(HomeActivity.this, "DB Exists", Toast.LENGTH_SHORT).show();
        }

    }

    public void setNotification(View V){
        Toast.makeText(HomeActivity.this, "setnotific", Toast.LENGTH_SHORT).show();
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        Toast.makeText(HomeActivity.this, Integer.toString(year) + Integer.toString(month) + Integer.toString(day), Toast.LENGTH_SHORT).show();
        c.set(Calendar.HOUR_OF_DAY, 19);
        c.set(Calendar.MINUTE, 23);
        c.set(Calendar.SECOND, 0);

        //c.set(2016,11,23,18,55,0);
        scheduleClient1.setAlarmForNotification(c,0);
        Toast.makeText(HomeActivity.this, "setnotific1", Toast.LENGTH_SHORT).show();

        //c.set(2016,11,23,18,56,0);
        c.set(Calendar.HOUR_OF_DAY, 19);
        c.set(Calendar.MINUTE, 24);
        c.set(Calendar.SECOND, 0);
        scheduleClient2.setAlarmForNotification(c,1);
        Toast.makeText(HomeActivity.this, "setnotific2", Toast.LENGTH_SHORT).show();

        //c.set(2016,11,23,18,47,0);
        c.set(Calendar.HOUR_OF_DAY, 19);
        c.set(Calendar.MINUTE, 25);
        c.set(Calendar.SECOND, 0);
        scheduleClient3.setAlarmForNotification(c,2);
        Toast.makeText(HomeActivity.this, "setnotific3", Toast.LENGTH_SHORT).show();


        // Ask our service to set an alarm for that date, this activity talks to the client that talks to the service
        //scheduleClient.setAlarmForNotification(c);
        //scheduleClient.setRepAlarm(c);

        // Get the date from our datepicker
    }

    public void removeNotification(View V) {
        Toast.makeText(HomeActivity.this, "removenotific", Toast.LENGTH_SHORT).show();
        scheduleClient1.removeAlarmNotification(0);
        scheduleClient2.removeAlarmNotification(1);
        scheduleClient3.removeAlarmNotification(2);

    }

    protected void onStop() {
        // When our activity is stopped ensure we also stop the connection to the service
        // this stops us leaking our activity into the system *bad*
        if(scheduleClient1 != null)
            scheduleClient1.doUnbindService();
        if(scheduleClient2 != null)
            scheduleClient2.doUnbindService();
        if(scheduleClient3 != null)
            scheduleClient3.doUnbindService();
        super.onStop();
    }

    public void initiatePrefValues() {
        SharedPreferences settings;
        Editor editor;
        settings = getSharedPreferences("weightify_prefs", Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2

        editor.putString("breakfast", "8:00"); //3
        editor.putString("snack1", "10:15");
        editor.putString("snack2", "15:45");
        editor.putString("snack3", "20:30");
        editor.putString("lunch", "12:30");
        editor.putString("dinner", "19:00");
        editor.putString("rowcount", "1");
        editor.commit(); //4

        Toast.makeText(HomeActivity.this, "setnotific", Toast.LENGTH_SHORT).show();

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


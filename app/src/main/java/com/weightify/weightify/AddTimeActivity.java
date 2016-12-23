package com.weightify.weightify;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

//import com.weightify.weightify.ScheduleClient;
import java.util.Calendar;
import java.util.Date;

public class AddTimeActivity extends AppCompatActivity {


    private ScheduleClient scheduleClient1;
    private ScheduleClient scheduleClient2;
    private ScheduleClient scheduleClient3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_time);

        scheduleClient1 = new ScheduleClient(this);
        scheduleClient1.doBindService();
        scheduleClient2 = new ScheduleClient(this);
        scheduleClient2.doBindService();
        scheduleClient3 = new ScheduleClient(this);
        scheduleClient3.doBindService();
        //scheduleClient = new ScheduleClient(this);
        //scheduleClient.doBindService();

    }

    public void insertTime(View v){
        Spinner spMeal = (Spinner) findViewById(R.id.spMeal);
        TimePicker tpTime = (TimePicker) findViewById(R.id.tpTime);
        DataBaseHelper myDbHelper = new DataBaseHelper(getApplicationContext());

        myDbHelper = new DataBaseHelper(this);

        String mealHour = tpTime.getCurrentHour().toString();
        String mealMin = tpTime.getCurrentMinute().toString();
        String mealTime = mealHour + ":" + mealMin;

        String meals = spMeal.getSelectedItem().toString();
        //Toast.makeText(AddTimeActivity.this, meals, Toast.LENGTH_SHORT).show();
        //Toast.makeText(AddTimeActivity.this, mealTime, Toast.LENGTH_SHORT).show();


//        <item>Breakfast</item>
//        <item>Morning Snack</item>
//        <item>Lunch</item>
//        <item>Afternoon Snack</item>
//        <item>Dinner</item>
//        <item>Dinner Snack</item>

        if (meals.equals("Breakfast")){
            meals = "breakfast";
        }else if (meals.equals("Lunch")) {
            meals = "lunch";
        }else if (meals.equals("Dinner")) {
            meals = "dinner";
        }else if (meals.equals("Morning Snack")) {
            meals = "snack1";
        }else if (meals.equals("Afternoon Snack")) {
            meals = "snack2";
        }else if (meals.equals("Dinner Snack")) {
            meals = "snack3";
        }

        Toast.makeText(AddTimeActivity.this, meals, Toast.LENGTH_SHORT).show();
        myDbHelper.insertDb(meals, mealTime);


        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = getSharedPreferences("weightify_prefs", Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2
        Toast.makeText(AddTimeActivity.this, myDbHelper.getDBData("breakfast"), Toast.LENGTH_SHORT).show();
        Toast.makeText(AddTimeActivity.this, myDbHelper.getDBData("snack1"), Toast.LENGTH_SHORT).show();
        Toast.makeText(AddTimeActivity.this, myDbHelper.getDBData("lunch"), Toast.LENGTH_SHORT).show();
        Toast.makeText(AddTimeActivity.this, myDbHelper.getDBData("snack2"), Toast.LENGTH_SHORT).show();
        Toast.makeText(AddTimeActivity.this, myDbHelper.getDBData("dinner"), Toast.LENGTH_SHORT).show();
        Toast.makeText(AddTimeActivity.this, myDbHelper.getDBData("snack3"), Toast.LENGTH_SHORT).show();


        editor.putString("breakfast", myDbHelper.getDBData("breakfast")); //3
        editor.putString("snack1", myDbHelper.getDBData("snack1"));
        editor.putString("snack2", myDbHelper.getDBData("snack2"));
        editor.putString("snack3", myDbHelper.getDBData("snack3"));
        editor.putString("lunch", myDbHelper.getDBData("lunch"));
        editor.putString("dinner", myDbHelper.getDBData("dinner"));
        editor.putString("dinner", myDbHelper.getDBData("dinner"));
        int rowCount = myDbHelper.getTotalUniqRows();
        editor.putString("rowcount", Integer.toString(rowCount));
        editor.commit(); //4

        removeNotification();
        setNotification();

    }

    public void setNotification(){

        SharedPreferences settings;
        settings = getSharedPreferences("weightify_prefs", Context.MODE_PRIVATE); //1
        String bf = settings.getString("breakfast", "8:00");
//        String s1 = settings.getString("snack1", "10:15");
//        String s2 = settings.getString("snack2", "15:45");
//        String s3 = settings.getString("snack3", "20:30");
        String lun = settings.getString("lunch", "12:30");
        String dine = settings.getString("dinner", "19:00");

//        String[] splittime = data.split(":");
//        hours += Integer.valueOf(splittime[0]);
//        mins += Integer.valueOf(splittime[1]);

        Toast.makeText(AddTimeActivity.this, "setnotific", Toast.LENGTH_SHORT).show();
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH) ;

        Toast.makeText(AddTimeActivity.this, Integer.toString(year) + Integer.toString(month) + Integer.toString(day), Toast.LENGTH_SHORT).show();
        c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(bf.split(":")[0]));
        c.set(Calendar.MINUTE, Integer.parseInt(bf.split(":")[1]));
        c.set(Calendar.SECOND, 0);

        //c.set(2016,11,23,18,55,0);
        scheduleClient1.setAlarmForNotification(c,0);
        Toast.makeText(AddTimeActivity.this, "setnotific1", Toast.LENGTH_SHORT).show();

        //c.set(2016,11,23,18,56,0);
        c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(lun.split(":")[0]));
        c.set(Calendar.MINUTE, Integer.parseInt(lun.split(":")[0]));
        c.set(Calendar.SECOND, 0);
        scheduleClient2.setAlarmForNotification(c,1);
        Toast.makeText(AddTimeActivity.this, "setnotific2", Toast.LENGTH_SHORT).show();

        //c.set(2016,11,23,18,47,0);
        c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(dine.split(":")[0]));
        c.set(Calendar.MINUTE, Integer.parseInt(dine.split(":")[0]));
        c.set(Calendar.SECOND, 0);
        scheduleClient3.setAlarmForNotification(c,2);
        Toast.makeText(AddTimeActivity.this, "setnotific3", Toast.LENGTH_SHORT).show();


        // Ask our service to set an alarm for that date, this activity talks to the client that talks to the service
        //scheduleClient.setAlarmForNotification(c);
        //scheduleClient.setRepAlarm(c);

        // Get the date from our datepicker
    }

    public void removeNotification() {
        Toast.makeText(AddTimeActivity.this, "removenotific", Toast.LENGTH_SHORT).show();
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
}

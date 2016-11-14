package com.weightify.weightify;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

public class AddTimeActivity extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_time);

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
        Toast.makeText(AddTimeActivity.this, meals, Toast.LENGTH_SHORT).show();
        Toast.makeText(AddTimeActivity.this, mealTime, Toast.LENGTH_SHORT).show();


//        <item>Breakfast</item>
//        <item>Morning Snack</item>
//        <item>Lunch</item>
//        <item>Afternoon Snack</item>
//        <item>Dinner</item>
//        <item>Dinner Snack</item>

        if (meals == "Breakfast"){
            meals = "breakfast";
        }else if (meals == "Lunch") {
            meals = "lunch";
        }else if (meals == "Dinner") {
            meals = "dinner";
        }else if (meals == "Morning Snack") {
            meals = "snack1";
        }else if (meals == "Afternoon Snack") {
            meals = "snack2";
        }else if (meals == "Dinner Snack") {
            meals = "snack3";
        }

        myDbHelper.insertDb(meals, mealTime);
    }
}

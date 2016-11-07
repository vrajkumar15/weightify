package com.weightify.weightify;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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


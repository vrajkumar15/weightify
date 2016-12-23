package com.weightify.weightify;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.weightify.weightify.ScheduleClient;
import java.util.Calendar;

/**
 * Created by rajkumar_vijayan on 12/23/16.
 */
public class SetNotification extends BroadcastReceiver {
    private ScheduleClient scheduleClient;

    @Override
    public void onReceive(Context context, Intent intent) {
        scheduleClient = new ScheduleClient(context);
        scheduleClient.doBindService();
        setNotification();
        onStop();

        Log.d("-", "Receiver3");
    }

    public void setNotification(){
        //Toast.makeText(HomeActivity.this, "setnotific", Toast.LENGTH_SHORT).show();


        Calendar c = Calendar.getInstance();
        //int day = 10;
        //int month = 1;
        //int year = 2017;
        c.set(Calendar.YEAR, 2016);
        c.set(Calendar.MONTH, 11);
        c.set(Calendar.DAY_OF_MONTH, 22);
        //c.set(year, month, day);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 17);
        c.set(Calendar.SECOND, 0);
        //c.set(2017,11,22,10,42,0);
        // Ask our service to set an alarm for that date, this activity talks to the client that talks to the service
        scheduleClient.setAlarmForNotification(c, 0);

        // Get the date from our datepicker
    }

    protected void onStop() {
        // When our activity is stopped ensure we also stop the connection to the service
        // this stops us leaking our activity into the system *bad*
        if(scheduleClient != null)
            scheduleClient.doUnbindService();
        this.onStop();
    }

}



package com.weightify.weightify;

/**
 * Created by rajkumar_vijayan on 12/22/16.
 */

import java.util.Calendar;


import java.util.Calendar;
import java.util.Date;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.weightify.weightify.NotifyService;

/**
 * Set an alarm for the date passed into the constructor
 * When the alarm is raised it will start the NotifyService
 *
 * This uses the android build in alarm manager *NOTE* if the phone is turned off this alarm will be cancelled
 *
 * This will run on it's own thread.
 *
 * @author paul.blundell
 */
public class AlarmTask implements Runnable{
    // The date selected for the alarm
    private Calendar date = Calendar.getInstance();

    private final int id;
    // The android system alarm manager
    private final AlarmManager am;
    // Your context to retrieve the alarm manager from
    private final Context context;

    public AlarmTask(Context context, Calendar date, int id) {
        this.context = context;
        this.am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        this.date = date;
        this.id = id;
    }

    public AlarmTask(Context context, int id) {
        this.context = context;
        this.am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        this.id = id;
    }

    @Override
    public void run() {
        // Request to start are service when the alarm date is upon us
        // We don't start an activity as we just want to pop up a notification into the system bar not a full activity
        Intent intent = new Intent(context, NotifyService.class);
        intent.putExtra(NotifyService.INTENT_NOTIFY, true);
        intent.putExtra("ID", Integer.toString(id));
        PendingIntent pendingIntent = PendingIntent.getService(context, id, intent, 0);
        // Sets an alarm - note this alarm will be lost if the phone is turned off and on again
        //am.set(AlarmManager.RTC, date.getTimeInMillis(), pendingIntent);
        am.set(AlarmManager.RTC, date.getTimeInMillis(), pendingIntent);

    }

    public void runRem() {
        // Request to start are service when the alarm date is upon us
        // We don't start an activity as we just want to pop up a notification into the system bar not a full activity
        Intent intent = new Intent(context, NotifyService.class);
        //intent.putExtra(NotifyService.INTENT_NOTIFY, true);
        //intent.putExtra("ID", Integer.toString(id));
        PendingIntent pendingIntent = PendingIntent.getService(context, id, intent, 0);
        // Sets an alarm - note this alarm will be lost if the phone is turned off and on again
        //am.set(AlarmManager.RTC, date.getTimeInMillis(), pendingIntent);
        am.cancel(pendingIntent);
        pendingIntent.cancel();
    }

    public void runRep() {
        // Request to start are service when the alarm date is upon us
        // We don't start an activity as we just want to pop up a notification into the system bar not a full activity
        //Intent intent = new Intent(context, NotifyService.class);
        Intent intent = new Intent(context, SetNotification.class);
        //context.startActivity(intent);
        //intent.putExtra(SetAlarmActivity, true);
        //PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0)
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);;

        // Sets an alarm - note this alarm will be lost if the phone is turned off and on again
        am.setInexactRepeating(AlarmManager.RTC,date.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
        //am.set(AlarmManager.RTC, date.getTimeInMillis(), pendingIntent);
    }

}
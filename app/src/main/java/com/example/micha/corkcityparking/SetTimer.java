package com.example.micha.corkcityparking;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * Created by micha on 04/07/2017.
 */

public class SetTimer {
    AlarmManager alarmManager;
    Context context;
    private PendingIntent pendingIntent;


    public SetTimer(Context ctx){
        context = ctx;
        // this.milli = milli;
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    public void setNotifiyTimer(){

        Intent myIntent = new Intent(context, Notify.class);
        pendingIntent = PendingIntent.getBroadcast(context, 0, myIntent, 0);

        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                AlarmManager.INTERVAL_FIFTEEN_MINUTES, AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);
    }
}

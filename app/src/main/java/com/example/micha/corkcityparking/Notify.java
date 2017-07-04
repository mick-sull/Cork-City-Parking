package com.example.micha.corkcityparking;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ParseException;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.text.format.DateUtils;
import android.util.Log;

import com.example.micha.corkcityparking.models.Record;

import java.util.concurrent.TimeUnit;

import static android.app.Notification.PRIORITY_MAX;
import static com.example.micha.corkcityparking.Contants.ISRUNNING;

/**
 * Created by micha on 04/07/2017.
 */

public class Notify extends Service{
    AlarmManager alarmManager;
    Context ctx;
    CountDownTimer countDownTimer;
    NotificationCompat.Builder notificationBuilder;
    long timeRemainingMilli;
    Record carpark;
    NotificationManager notificationManager;
    boolean isRunning = false;


    public Notify(Context ctx){
        this.ctx = ctx;
    }



    public void displayNotification(Record carpark, int duration){
        this.carpark = carpark;
        if(countDownTimer != null) {
            countDownTimer.cancel();
        }
        timeRemainingMilli = 0;
        timeRemainingMilli = TimeUnit.MINUTES.toMillis(duration *60);

        startTimer();


    }

    private void startTimer() {
/*
        if(ISRUNNING) {
            countDownTimer.cancel();
            countDownTimer.onFinish();
            notificationManager.cancel(1);
            Log.d("TEST", "Timer STOPPED");
        }*/
        countDownTimer = new CountDownTimer(timeRemainingMilli, 1000) {
            public void onTick(long millisUntilFinished) {
                ISRUNNING = true;
                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
                Log.d("TEST", "seconds remiaing " +  + millisUntilFinished / 1000);
//                notificationBuilder.setContentText("seconds remaining: " + millisUntilFinished / 1000);
                timeRemainingMilli -= 1000;
                notificationBuilder = new NotificationCompat.Builder(ctx)
                        .setSmallIcon(R.drawable.ic_local_parking_black_24dp)
                        .setContentTitle("Cork City Parking - " + carpark.getName())
                        .setContentText("Leave carpark " + converteTimestamp(System.currentTimeMillis() + timeRemainingMilli) )
                        .setAutoCancel(false)
                        .setPriority(PRIORITY_MAX)
                        .setOngoing(true)
                        .setWhen(System.currentTimeMillis() + 1000);
                // .setAutoCancel(true)
                // .setSound(defaultSoundUri)
                //.setContentIntent(pendingIntentReqProfile);
                //.addAction(R.drawable.ic_check_black_24dp,"Accept",piAcceptFriendRequest);

                String ns = Context.NOTIFICATION_SERVICE;
                notificationManager =  (NotificationManager) ctx.getSystemService(ns);

                notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
            }
            public void onFinish() {
                ISRUNNING= false;
                notificationBuilder.setContentText("Done!");
            }
        }.start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public CharSequence converteTimestamp(long milli) throws ParseException {
        //return DateUtils.getRelativeTimeSpanString(Long.parseLong(String.valueOf(getTimeInMilliSeconds())), System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS);
        return DateUtils.getRelativeTimeSpanString(milli, System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS).toString().toLowerCase();
    }


/*    public long  getTimeInMilliSeconds() throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(getFormattedTime());
        return date.getTime();
    }*/
}

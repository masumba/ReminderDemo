package com.afrifuturae.reminderdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import java.util.Objects;

public class Main2Activity extends AppCompatActivity {

    RadioButton rdbNotification,rdbToast;
    Button btnOneTime,btnRepeating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        rdbNotification = (RadioButton)findViewById(R.id.radioNotification);
        rdbToast = (RadioButton)findViewById(R.id.radioToast);
        btnOneTime = (Button)findViewById(R.id.btnOneTime);
        btnRepeating = (Button)findViewById(R.id.btnRepeating);

        btnOneTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rdbNotification.isChecked()){
                    startAlarm(true,false);
                } else {
                    startAlarm(false,false);
                }
            }
        });

        btnRepeating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rdbNotification.isChecked()){
                    startAlarm(true,true);
                }else {
                    startAlarm(false,true );
                }
            }
        });
    }

    private void startAlarm(boolean notification, boolean repeat) {
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent;
        PendingIntent pendingIntent;
        if (!notification){
            intent = new Intent(Main2Activity.this,AlarmToastReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);
        } else {
            intent = new Intent(Main2Activity.this,AlarmNotificationReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);
        }

        if (!repeat){
            Objects.requireNonNull(alarmManager).set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+3000,pendingIntent);
        } else {
            Objects.requireNonNull(alarmManager).setRepeating(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime()+3000,3000,pendingIntent);
        }
    }
}

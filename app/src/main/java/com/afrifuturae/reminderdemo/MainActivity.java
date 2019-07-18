package com.afrifuturae.reminderdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Set Onclick Listener*/
        findViewById(R.id.btnSet).setOnClickListener(this);
        findViewById(R.id.btnCancel).setOnClickListener(this);
        findViewById(R.id.btnOther).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        EditText editText = findViewById(R.id.editTextTask);
        TimePicker timePicker = findViewById(R.id.timePicker);

        /*Set NotificationId & Text*/
        Intent intent = new Intent(MainActivity.this,AlarmReceiver.class);
        int notificationId = 1;
        intent.putExtra("notificationId", notificationId);
        intent.putExtra("todo",editText.getText().toString());

        /*Get Broadcast*/
        PendingIntent alarmIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        int i = v.getId();
        if (i == R.id.btnSet) {
            int hour = timePicker.getCurrentHour();
            int minute = timePicker.getCurrentMinute();

            /*Create Time*/
            Calendar startTime = Calendar.getInstance();
            startTime.set(Calendar.HOUR_OF_DAY, hour);
            startTime.set(Calendar.MINUTE, minute);
            startTime.set(Calendar.SECOND, 0);
            long alarmStartTime = startTime.getTimeInMillis();

            /*Set Alarm*/
            Objects.requireNonNull(alarmManager).set(AlarmManager.RTC_WAKEUP, alarmStartTime, alarmIntent);
            Toast.makeText(this, "Done!", Toast.LENGTH_SHORT).show();
        } else if (i == R.id.btnCancel) {
            Objects.requireNonNull(alarmManager).cancel(alarmIntent);
            Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
        } else if (i == R.id.btnOther){
            Intent intent1 = new Intent(MainActivity.this,Main2Activity.class);
            startActivity(intent1);
        }
    }
}

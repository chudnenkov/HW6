package com.example.roman.hw6;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class ReceiverStartService extends BroadcastReceiver {
    public ReceiverStartService() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")){
            //Intent intent1 = new Intent(context, ServiceGetWeather.class);
            //context.startService(intent1);

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.SECOND, 10);
            Intent intentWeather = new Intent(context, ServiceGetWeather.class);
            PendingIntent pintent = PendingIntent.getService(context, 0, intentWeather, 0);
            AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                    20*1000, pintent);
            context.startService(new Intent(context, ServiceGetWeather.class));
        }

    }
}

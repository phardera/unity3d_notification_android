package com.macaronics.notification;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;
import com.unity3d.player.UnityPlayer;

public class AlarmReceiver extends BroadcastReceiver {
    public static void startAlarm(String name, String title, String label, int secondsFromNow){
        Activity act =UnityPlayer.currentActivity;
        Log.i("Unity", "startAlarm...");
      
        Calendar c = Calendar.getInstance();
        c.add(Calendar.SECOND, secondsFromNow);
        long alarmTime = c.getTimeInMillis();
        Log.i("Unity", "alarm time +"+secondsFromNow);
        
        // Schedule the alarm!
        AlarmManager am = (AlarmManager)act.getSystemService(Context.ALARM_SERVICE);
        Intent ii =new Intent(act, AlarmReceiver.class);
        ii.putExtra("name", name);
        ii.putExtra("title", title);
        ii.putExtra("label", label);
        am.set(AlarmManager.RTC_WAKEUP, alarmTime, PendingIntent.getBroadcast(act, 0, ii, 0));
    }
    
    //<receiver android:process=":remote_notification" android:name="AlarmReceiver"></receiver>
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Unity", "Alarm Recieved!");
      
        NotificationManager mNM = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        
        Bundle bb =intent.getExtras();

        Class<?> cc = null;
        try {
          cc = context.getClassLoader().loadClass("com.unity3d.player.UnityPlayerProxyActivity");
        } catch (ClassNotFoundException e1) {
          e1.printStackTrace();
          return;
        }
            
        final PackageManager pm=context.getPackageManager();
        ApplicationInfo applicationInfo = null;
        try {
          applicationInfo = pm.getApplicationInfo(context.getPackageName(),PackageManager.GET_META_DATA);
        } catch (NameNotFoundException e) {
          e.printStackTrace();
          return;
        }
        final int appIconResId=applicationInfo.icon;
        Notification notification = new Notification(appIconResId, (String)bb.get("name"), System.currentTimeMillis());
        
        int id =(int)(Math.random()*10000.0f)+1;
        PendingIntent contentIntent = PendingIntent.getActivity(context, id, new Intent(context, cc), 0);
        notification.setLatestEventInfo(context, (String)bb.get("title"), (String)bb.get("label"), contentIntent);

        Log.i("Unity", "notify("+id+") with "+(String)bb.get("title")+", "+(String)bb.get("label"));
        mNM.notify(id, notification);
    }
}
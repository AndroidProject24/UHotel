package com.acuteksolutions.uhotel.utils;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import com.acuteksolutions.uhotel.BaseApplication;
import com.acuteksolutions.uhotel.ui.activity.MainActivity;

public class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    private Activity context;
    private Thread.UncaughtExceptionHandler defaultUEH;
    public MyUncaughtExceptionHandler(Activity context) {
        this.context = context;
        this.defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
    }

    public void uncaughtException(Thread thread, Throwable exception) {
            Intent intent = new Intent(context, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK
                    | Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(BaseApplication.getInstance().getBaseContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
            AlarmManager mgr = (AlarmManager) BaseApplication.getInstance().getBaseContext().getSystemService(
                Context.ALARM_SERVICE);
            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, pendingIntent);

            context.finish();
            System.exit(2);


    }

}
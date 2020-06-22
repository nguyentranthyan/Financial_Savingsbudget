package com.example.financial_savings.modules.alarms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.example.financial_savings.R;
import com.example.financial_savings.entities.NganSach;
import com.example.financial_savings.helper.DBHelper;
import com.example.financial_savings.notifications.NotifierAlarm_budget;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static android.content.ContentValues.TAG;

public class AlarmbudgetModule {
    public static void handlingAlarmRepeat_Date_budget(java.sql.Date dateNew, Context activity, NganSach nganSach, DBHelper dbHelper) {
        Calendar calendarDate = Calendar.getInstance();
        calendarDate.setTime(dateNew);
        int year = calendarDate.get(Calendar.YEAR);
        int month = calendarDate.get(Calendar.MONTH) ;
        int dayOfMonth = calendarDate.get(Calendar.DAY_OF_MONTH);
        handlingAlarmRepeat_TimeZonebudget(year, month, dayOfMonth, activity, nganSach, dbHelper, dateNew);
    }
    private static void handlingAlarmRepeat_TimeZonebudget(int year, int month, int dayOfMonth, Context activity,
                                                           NganSach nganSach, DBHelper dbHelper, java.sql.Date dateNew) {

        final String TITLE = activity.getResources().getString(R.string.repeat_title_budget);
        final String MESSAGE = activity.getResources().getString(R.string.repeat_message_budget);
        final String DETAILS = activity.getResources().getString(R.string.alarm_detail);

        final String message = MESSAGE + ". " + DETAILS;

        Calendar newDate = Calendar.getInstance();
        newDate.set(year, month, dayOfMonth);

        Log.d("time", "handlingAlarmRepeat_TimeZonebudget: "+year+month+dayOfMonth);
        Date remind = new Date(newDate.getTime().toString());

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:30"));
        calendar.setTime(remind);
        calendar.set(Calendar.SECOND, 0);

        Intent intent = new Intent(activity, NotifierAlarm_budget.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("id",nganSach.getMaNganSach());
        intent.putExtra("title", TITLE);
        intent.putExtra("message", message);
        intent.putExtra("dateNew", dateNew.toString());

        PendingIntent intent1 = PendingIntent.getBroadcast(activity, 2222,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), intent1);
        }
    }
}

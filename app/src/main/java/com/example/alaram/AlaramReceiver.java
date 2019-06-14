package com.example.alaram;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.util.Calendar;

public class AlaramReceiver extends BroadcastReceiver {

    public static final String TYPE_ONE_TIME ="OneTimeAlaram";
    public static final String TYPE_REPEATING = "RepeatingAlaram";
    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_TYPE = "type";
    private final int NOTIF_ID_ONETIME = 100;
    private final int NOTIF_ID_REPEATING = 101;

    public AlaramReceiver(){

    }
    @Override
    public void onReceive(Context context, Intent intent) {
        String type = intent.getStringExtra(EXTRA_TYPE);
        String message = intent.getStringExtra(EXTRA_MESSAGE);
        String title = type.equalsIgnoreCase(TYPE_ONE_TIME)? "One Time Alaram" : "Repeating Alaram";
        int notifId = type.equalsIgnoreCase(TYPE_ONE_TIME)? NOTIF_ID_ONETIME : NOTIF_ID_REPEATING;

        showAlaramNotification (context, title, message, notifId);
    }

    private void showAlaramNotification(Context context, String title, String message, int notifId) {

        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alaramSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                                            .setSmallIcon(R.mipmap.ic_launcher)//nanti di ganti
                                            .setContentTitle(title)
                                            .setContentText(message)
                                            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                                            .setVibrate(new long []{1000, 1000, 1000, 1000})
                                            .setSound(alaramSound);
        notificationManagerCompat.notify(notifId, builder.build());
    }

    public void setOneTimeAlaram(Context context, String typeOneTime, String oneTimeDate, String oneTimeTime, String type){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlaramReceiver.class);
        String message = "message";
        String date = "date";
        String time = "time";
        intent.putExtra(EXTRA_MESSAGE,message);
        intent.putExtra(EXTRA_TYPE, type);
        String dateArray[] = date.split("-");
        String timeArray[] = time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Integer.parseInt(dateArray[0]));
        calendar.set(Calendar.MONTH, Integer.parseInt(dateArray[1])-1);
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateArray[2]));
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE,Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND,0);
        int requestCode = NOTIF_ID_ONETIME;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),pendingIntent);

        Toast.makeText(context,"One time alaram set up", Toast.LENGTH_SHORT).show();
    }

    public void cancelAlaram (Context context, String type){
        AlarmManager alaramManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlaramReceiver.class);
        int requestCode = type.equalsIgnoreCase(TYPE_ONE_TIME)?NOTIF_ID_ONETIME : NOTIF_ID_REPEATING;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,requestCode,intent,0);
        alaramManager.cancel(pendingIntent);
        Toast.makeText(context, "repeating alaram dibatalkan",Toast.LENGTH_SHORT).show();
    }
}

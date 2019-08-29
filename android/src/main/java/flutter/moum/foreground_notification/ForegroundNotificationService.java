package flutter.moum.foreground_notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import static flutter.moum.foreground_notification.ForegroundNotificationPlugin.context;

public class ForegroundNotificationService extends Service {

    private static final String TAG = "ForegroundNotificationS";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        showNotification();

        return START_STICKY;
    }

    private void showNotification() {
        Intent intent = getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        intent.setAction("SELECT_NOTIFICATION");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 207, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        int resourceId = context.getResources().getIdentifier("app_icon", "drawable", context.getPackageName());

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "exampleChannelId")
                .setSmallIcon(resourceId)
                .setContentTitle(NotificationDetails.title)
                .setContentText(NotificationDetails.message)
                .setVibrate(new long[]{ 0 })
//                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon_logo_shadow))
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setUsesChronometer(NotificationDetails.useChronometer)
                .setWhen(NotificationDetails.when == 0 ? System.currentTimeMillis() : NotificationDetails.when)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(""));

//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//        notificationManager.notify(10001, mBuilder.build());

        Log.d(TAG, "showNotification: builder!!");
        startForeground(1010, mBuilder.build());

    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: service is destroyed");
        stopForeground(true);
        stopSelf();
        super.onDestroy();
    }
}

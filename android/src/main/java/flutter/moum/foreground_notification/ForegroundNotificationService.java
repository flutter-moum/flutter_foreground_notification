package flutter.moum.foreground_notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

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
        Intent intent = getPackageManager().getLaunchIntentForPackage("flutter.moum.foreground_notification_example");
        intent.setAction("SELECT_NOTIFICATION");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 207, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "passionNotification2")
//                .setSmallIcon(R.drawable.ic_notification_small)
                .setContentTitle("시간 측정 중")
                .setContentText("천재는 계속해서 노력할 수 있는 재능이다")
                .setVibrate(new long[]{ 0 })
//                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon_logo_shadow))
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setUsesChronometer(true)
                .setWhen(System.currentTimeMillis())
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(""));

//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//        notificationManager.notify(10001, mBuilder.build());

        startForeground(1001, mBuilder.build());

    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: service is destroyed");
        stopForeground(true);
        stopSelf();
        super.onDestroy();
    }
}

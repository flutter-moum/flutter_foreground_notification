package flutter.moum.foreground_notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** ForegroundNotificationPlugin */
public class ForegroundNotificationPlugin implements MethodCallHandler {
  /** Plugin registration. */

  static Context context;
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "com.flutter.moum/foreground_notification");
    channel.setMethodCallHandler(new ForegroundNotificationPlugin());
    context = registrar.activeContext();

  }

  private void setNotificationChannel() {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      NotificationChannel channel = new NotificationChannel("passionNotification2", "passion", NotificationManager.IMPORTANCE_LOW);
      channel.setDescription("passion indicator notification channel");
      // Register the channel with the system; you can't change the importance
      // or other notification behaviors after this
      NotificationManager notificationManager = (android.app.NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
      notificationManager.createNotificationChannel(channel);
    }

  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    if (call.method.equals("showNotification")) {
      Intent serviceIntent = new Intent(context, ForegroundNotificationService.class);
      context.startService(serviceIntent);

    } else if (call.method.equals("closeNotification")) {
      Intent closeIntent = new Intent(context, ForegroundNotificationService.class);
      context.stopService(closeIntent);

    } else {
      result.notImplemented();
    }
  }
}

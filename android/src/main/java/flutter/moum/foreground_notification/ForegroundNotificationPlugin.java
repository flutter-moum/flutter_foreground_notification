package flutter.moum.foreground_notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.Map;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** ForegroundNotificationPlugin */
public class ForegroundNotificationPlugin implements FlutterPlugin {
  /** Plugin registration. */

  private static final String TAG = "ForegroundNotificationP";

//  static Context context;
  public static void registerWith(Registrar registrar) {

    ForegroundNotificationPlugin plugin = new ForegroundNotificationPlugin();
    plugin.setupMethodChannels(registrar.context(), registrar.messenger());
    setNotificationChannel(registrar.context());

  }

  private void setupMethodChannels(final Context context, BinaryMessenger messenger) {
    final MethodChannel channel = new MethodChannel(messenger, "com.flutter.moum/foreground_notification");
    channel.setMethodCallHandler(new MethodCallHandler() {
      @Override
      public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        Log.d(TAG, "onMethodCall: "+call.method);

        if (call.method.equals("initialize")) {

          Map<String, Object> arguments = call.arguments();
          NotificationDetails.title = (String) arguments.get("title");
          NotificationDetails.message = (String) arguments.get("message");
          NotificationDetails.useChronometer = (boolean) arguments.get("useChronometer");
          NotificationDetails.when = (long) arguments.get("when");

        } else if (call.method.equals("showNotification")) {
          Intent serviceIntent = new Intent(context, ForegroundNotificationService.class);
          context.startService(serviceIntent);
          result.success(true);


        } else if (call.method.equals("closeNotification")) {
          Intent closeIntent = new Intent(context, ForegroundNotificationService.class);
          context.stopService(closeIntent);
          result.success(true);
        } else {
          result.notImplemented();
        }
      }
    });
  }

  private static void setNotificationChannel(Context context) {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      NotificationChannel channel = new NotificationChannel("exampleChannelId", "passion", NotificationManager.IMPORTANCE_LOW);
      channel.setDescription("passion indicator notification channel");
      // Register the channel with the system; you can't change the importance
      // or other notification behaviors after this
      NotificationManager notificationManager = (android.app.NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
      notificationManager.createNotificationChannel(channel);
    }

  }


  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding binding) {
    final Context context = binding.getApplicationContext();
    setupMethodChannels(context, binding.getBinaryMessenger());
    setNotificationChannel(context);
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {

  }
}

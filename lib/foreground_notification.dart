import 'dart:async';
import 'dart:io';

import 'package:flutter/services.dart';

class ForegroundNotification {
  static const MethodChannel _channel =
      const MethodChannel('com.flutter.moum/foreground_notification');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static void showAOSNotification() {
    if (Platform.isAndroid) _channel.invokeMethod("showNotification");
  }
  static void closeAOSNotification() {
    if (Platform.isAndroid) _channel.invokeMethod("closeNotification");
  }
}

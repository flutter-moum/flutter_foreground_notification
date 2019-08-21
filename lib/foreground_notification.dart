import 'dart:async';
import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

typedef OnNotificationCallback = Future<dynamic> Function(String payload);

class ForegroundNotification {

  factory ForegroundNotification() => _instance;

  @visibleForTesting
  ForegroundNotification.private(
      MethodChannel channel) : _channel = channel;

  static final ForegroundNotification _instance =
  ForegroundNotification.private(const MethodChannel('com.flutter.moum/foreground_notification'));

  final MethodChannel _channel;

  OnNotificationCallback onNotificationCallback;


  Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  Future<bool> initialize({OnNotificationCallback onNotification}) async {
    onNotificationCallback = onNotification;
    _channel.setMethodCallHandler(_handleMethod);

    var result = await _channel.invokeMethod('initialize', {'appIcon': 'appIcon'});
    return result;
  }

  void showAOSNotification() {
    if (Platform.isAndroid) _channel.invokeMethod("showNotification");
  }
  void closeAOSNotification() {
    if (Platform.isAndroid) _channel.invokeMethod("closeNotification");
  }

  Future<void> _handleMethod(MethodCall call) {
    switch (call.method) {
      case 'selectNotification':
        return onNotificationCallback(call.arguments);
      default:
        return Future.error('method not defined');
    }
  }

}

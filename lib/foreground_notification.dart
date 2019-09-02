import 'dart:async';
import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:foreground_notification/initializationSettings.dart';

typedef OnNotificationCallback = Future<dynamic> Function(String payload);

class ForegroundNotification {
  factory ForegroundNotification() => _instance;

  @visibleForTesting
  ForegroundNotification.private(MethodChannel channel) : _channel = channel;

  // Singletone instance
  static final ForegroundNotification _instance =
      ForegroundNotification.private(
          const MethodChannel('com.flutter.moum/foreground_notification'));

  final MethodChannel _channel;

  OnNotificationCallback onNotificationCallback;

  bool _isShowing;

  /// get state if notification showing
  bool get isShowing => _isShowing;

  Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }


  // Initialize notification details
  Future<bool> initialize({
    @required OnNotificationCallback selectNotification,
    @required String title,
    @required String message,
    bool useChronometer = false,
    int when = 0,
  }) async {
    onNotificationCallback = selectNotification;
    _channel.setMethodCallHandler(_handleMethod);

    Map<String, dynamic> serializedInitialSettings =
        InitializationSettings(title, message, useChronometer, when).toMap();

    var result =
        await _channel.invokeMethod('initialize', serializedInitialSettings);
    return result;
  }

  // If notification is to show
  Future<bool> showAOSNotification() async {
    if (Platform.isAndroid) {
      var result = await _channel.invokeMethod("showNotification");
      if (result) _isShowing = true;
      return result;
    }
    return Future.value(false);
  }

  // If notification is to close
  Future<bool> closeAOSNotification() async {
    if (Platform.isAndroid) {
      var result = await _channel.invokeMethod("closeNotification");
      if (result) _isShowing = false;
      return result;
    }
    return Future.value(false);
  }

  // set handle method to do something when notification is selected
  Future<void> _handleMethod(MethodCall call) {
    switch (call.method) {
      case 'selectNotification':
        return onNotificationCallback(call.arguments);
      default:
        return Future.error('method not defined');
    }
  }
}

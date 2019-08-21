import 'dart:async';
import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class ForegroundNotification {

  factory ForegroundNotification() => _instance;

  @visibleForTesting
  ForegroundNotification.private(
      MethodChannel channel) : _channel = channel;

  static final ForegroundNotification _instance =
  ForegroundNotification.private(const MethodChannel('com.flutter.moum/foreground_notification'));

  final MethodChannel _channel;


  Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  void showAOSNotification() {
    if (Platform.isAndroid) _channel.invokeMethod("showNotification");
  }
  void closeAOSNotification() {
    if (Platform.isAndroid) _channel.invokeMethod("closeNotification");
  }
}

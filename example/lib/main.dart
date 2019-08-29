import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:foreground_notification/foreground_notification.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';

  ForegroundNotification foregroundNotification;

  @override
  void initState() {
    super.initState();
    foregroundNotification = ForegroundNotification();
    ForegroundNotification().initialize(
        selectNotification: null,
        title: 'Flutter Test',
        message: 'Flutter Message Body',
        useChronometer: true,
        when: DateTime.now().millisecondsSinceEpoch,
    );
//    initPlatformState();
  }
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Forground Service Notification'),
        ),
        body: Center(
          child: IconButton(icon: Icon(Icons.play_circle_filled), onPressed: () => foregroundNotification.showAOSNotification()),
        ),
      ),
    );
  }

  _show() {
    foregroundNotification.showAOSNotification();
  }
}

import 'package:flutter/material.dart';
import 'package:foreground_notification/foreground_notification.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  ForegroundNotification foregroundNotification;
  bool isRunning = false;

  @override
  void initState() {
    super.initState();
    foregroundNotification = ForegroundNotification();
    ForegroundNotification().initialize(
      selectNotification: null,
      title: 'Flutter Notification Title',
      message: 'Flutter Message Body',
      useChronometer: false,
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
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              icon(),
              SizedBox(
                height: 10.0,
              ),
              Text(isRunning ? 'Close' : 'Show'),
            ],
          ),
        ),
      ),
    );
  }

  Widget icon() {
    return isRunning
        ? IconButton(
            icon: Icon(
              Icons.play_circle_outline,
              size: 40.0,
              color: Colors.grey,
            ),
            onPressed: () async {
              var result = await foregroundNotification.closeAOSNotification();
              print('1111111111');
              print(result);
              setState(() {
                isRunning = !isRunning;
              });
            })
        : IconButton(
            icon: Icon(
              Icons.pause_circle_outline,
              size: 40.0,
              color: Colors.grey,
            ),
            onPressed: () async {
              var result = await foregroundNotification.showAOSNotification();
              print('22222222222');
              print(result);
              setState(() {
                isRunning = !isRunning;
              });
            });
  }
}

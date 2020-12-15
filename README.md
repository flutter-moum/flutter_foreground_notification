# Foreground Service Notification Plugin

**Migrate to AndroidX**

A Flutter plugin to show foreground service notification and customize it. (iOS is not supported)

If you need present that your app is running as a service in user's phone, you can use this plugin.

## Usage
To use this plugin, add `foreground_service_notification` as a [dependency in your pubspec.yaml file](https://flutter.io/platform-plugins/).

### Example (see file in example/lib/main.dart)

``` dart
    // Import package
    import 'package:foreground_notification/foreground_notification.dart';

    // Instantiate it
    ForegroundNotification foregroundNotification;
    foregroundNotification = ForegroundNotification();
    ForegroundNotification().initialize(
      selectNotification: null,
      // input your notification title
      title: 'Flutter Notification Title',
      // input your notification message body
      message: 'Flutter Message Body',
      // if you need chronometer, set value true (* chronometer is an extremely accurate clock)
      useChronometer: false,
      // if you set chronometer true, set the time you want to start
      when: DateTime.now().millisecondsSinceEpoch,
    );

    /// show notification
    foregroundNotification.showAOSNotification();

    /// close notification
    foregroundNotification.closeAOSNotification();
```

When you set the `useChronometer` true and set the start time, the seconds start to move from the time you set.
if you do not set the start time(`when` field), it is initialized to current time.

**If you want to show this notification, you first set up your app icon image in folder `your_app/android/app/src/main/res/drawable/app_icon.png`
Image must be named 'app_icon.png'**

 - [x] Chronometer
 - [x] Small Icon
 - [ ] Large Icon

class InitializationSettings {
  final String title;
  final String message;
  final bool useChronometer;
  final int when;

  // Initialization Settings
  const InitializationSettings(
      this.title, this.message, this.useChronometer, this.when);

  // Initialized details to map
  Map<String, dynamic> toMap() {
    return <String, dynamic>{
      'appIcon': 'appIcon',
      'title': this.title,
      'message': this.message,
      'useChronometer': this.useChronometer,
      'when': this.when
    };
  }
}

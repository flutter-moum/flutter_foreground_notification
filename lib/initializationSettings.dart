
class InitializationSettings {

  final String title;
  final String message;
  final bool useChronometer;
  final int when;

  const InitializationSettings(this.title, this.message, this.useChronometer, this.when);

  Map<String, dynamic> toMap() {
    return <String, dynamic>{'appIcon':'appIcon', 'title': this.title, 'message': this.message, 'useChronometer' : this.useChronometer, 'when' : this.when};
  }
}

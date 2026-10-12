# Module herald-kmp-core

Herald's types for shared Kotlin code: events, properties and the five capabilities.

- On Android, each type is the Android SDK's own, so events from shared code reach the Android
  Herald unchanged.
- On iOS, `IosAnalytics` sends the calls to herald-ios through its `HeraldBridge`.

# Change Log

## Version 1.1.0

_Not released yet_

 * New: `herald-kmp-meta`: Meta's purchase event for shared code, made with
   `metaPurchaseEvent(…)`. It needs the Android SDK 1.4.0.

## Version 1.0.0

_2026-10-12_

The first release: Herald's API in shared Kotlin, over the Android SDK 1.2.0 and the iOS SDK 1.1.0.

 * New: `herald-kmp-core`: events, properties and the five capabilities for shared code. On
   Android they are the Android SDK's own types; on iOS, `IosAnalytics` sends the calls to
   herald-ios through its `HeraldInterop` bridge.
 * New: `herald-kmp-amplitude`, `herald-kmp-adjust` and `herald-kmp-appsflyer`: the vendors'
   revenue events, made with functions such as `amplitudeRevenueEvent(…)`.

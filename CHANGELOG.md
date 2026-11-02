# Change Log

## Version 1.2.0

_2026-11-02_

 * Breaking: herald-kmp needs Android 7 (API 24) or later, up from Android 6 (API 23), as the
   Android SDK 1.5.0 does.
 * Upgrade: the Android SDK 1.5.0 and the iOS SDK 1.4.0. Both turn off what Meta's SDK 19 collects
   by itself when consent is off, and the iOS SDK's Meta package needs Meta's iOS SDK 19.

## Version 1.1.0

_2026-10-18_

 * New: `herald-kmp-meta`: Meta's purchase event for shared code, made with
   `metaPurchaseEvent(…)`. It needs the Android SDK 1.4.0 and the iOS SDK 1.3.0, where
   `herald-ios-meta` is.
 * New: the sample sends a Meta purchase from shared code, and its iOS app translates it.

## Version 1.0.0

_2026-10-12_

The first release: Herald's API in shared Kotlin, over the Android SDK 1.2.0 and the iOS SDK 1.1.0.

 * New: `herald-kmp-core`: events, properties and the five capabilities for shared code. On
   Android they are the Android SDK's own types; on iOS, `IosAnalytics` sends the calls to
   herald-ios through its `HeraldInterop` bridge.
 * New: `herald-kmp-amplitude`, `herald-kmp-adjust` and `herald-kmp-appsflyer`: the vendors'
   revenue events, made with functions such as `amplitudeRevenueEvent(…)`.

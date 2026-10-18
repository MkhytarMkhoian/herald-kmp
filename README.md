<picture>
  <source media="(prefers-color-scheme: dark)" srcset="https://raw.githubusercontent.com/MkhytarMkhoian/herald/main/docs/assets/readme-banner-dark.png">
  <img alt="Herald" src="https://raw.githubusercontent.com/MkhytarMkhoian/herald/main/docs/assets/readme-banner-light.png">
</picture>

> *A herald announces an event to whoever is listening.*

[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](LICENSE)
[![Docs](https://img.shields.io/badge/docs-website-indigo.svg)](https://mkhytarmkhoian.github.io/herald-docs/)

Herald is an analytics library for mobile apps. Your app describes what happened as an event, and
Herald sends that event to every analytics service you use: Firebase, Adjust, Mixpanel, AppsFlyer,
Amplitude, Meta, or one you build yourself.

This repository is **Herald for Kotlin Multiplatform**: write your events once, in shared Kotlin,
and track them from shared view models. It wraps the two native SDKs, so each platform keeps its
own setup and vendors:

- on Android, the [Android SDK](https://github.com/MkhytarMkhoian/herald): shared events *are*
  Android Herald events;
- on iOS, the [iOS SDK](https://github.com/MkhytarMkhoian/herald-ios), through its
  `HeraldInterop` bridge.

| herald-kmp | Android SDK (`herald`) | iOS SDK (`herald-ios`) |
| --- | --- | --- |
| 1.0.0 | 1.2.0 | 1.1.0 |

## Install

In your shared module:

```kotlin
kotlin {
    listOf(iosArm64(), iosSimulatorArm64()).forEach { target ->
        target.binaries.framework {
            baseName = "Shared"
            export("io.github.mkhytarmkhoian:herald-kmp-core:1.0.0") // so Swift can use it
        }
    }
    sourceSets {
        commonMain.dependencies {
            api("io.github.mkhytarmkhoian:herald-kmp-core:1.0.0")
        }
    }
}
```

For revenue, add the vendor's module too: `herald-kmp-amplitude`, `herald-kmp-adjust`,
`herald-kmp-appsflyer` or `herald-kmp-meta`, in `commonMain` and in `export(...)`.

## Shared code

The same Kotlin as on Android:

```kotlin
class CheckoutStarted(plan: String) : Event {
    override val name = "checkout_started"
    override val parameters = parameters { put("plan", plan) }
}

class CheckoutViewModel(private val analytics: EventTrackerService) {
    suspend fun started(plan: String) = analytics.track(CheckoutStarted(plan))

    suspend fun bought(plan: String) = analytics.track(
        amplitudeRevenueEvent(name = "purchase", price = 4.99, productId = plan, currency = "USD"),
    )
}
```

Two things differ from Android, because of Kotlin limits:

- A property's value is `AnalyticsValues.string("dark")`, not `AnalyticsValue.String("dark")`.
- A revenue event is made by the function named after its class, with a small first letter:
  `amplitudeRevenueEvent(…)`, `adjustAdRevenueEvent(…)`, `appsFlyerPurchaseEvent(…)`. It has the
  same parameters and defaults as the Android constructor.

## Android

Nothing extra. Set Herald up as in the Android guide and give it to your shared code:

```kotlin
val herald = Herald {
    provider("amplitude", events = amplitudeTracker /* … */)
}
CheckoutViewModel(analytics = herald)
```

## iOS

1. In Xcode, add `herald-ios` with its `HeraldCore` and `HeraldInterop` modules, and your vendors'
   packages, as for any iOS app.
2. Copy two files into your app, once:
   - [`HeraldKmpBridge.swift`](sample/iosApp/App/HeraldKmpBridge.swift), as is;
   - [`RevenueTranslation.swift`](sample/iosApp/App/RevenueTranslation.swift), keeping the blocks
     for your vendors. It turns the shared revenue events into herald-ios's.
3. Set herald-ios up as in the iOS guide, then connect it to your shared code:

```swift
let herald = Herald(providers: [amplitudeProvider(amplitude)])
let bridge = HeraldBridge(herald, translate: revenueEvent, errorReporter: report)
let viewModel = CheckoutViewModel(analytics: IosAnalytics(bridge: HeraldKmpBridge(bridge)))
```

If `RevenueTranslation.swift` misses a revenue event, the bridge reports it to `errorReporter`
instead of letting the revenue disappear without a word.

herald-ios has types with the same names as herald-kmp's, so Swift sees the Kotlin ones with
"Kotlin" in front: `KotlinEvent`, `KotlinAmplitudeRevenueEvent` and so on.

## Labels

A label is an interface your events implement, such as `interface PersonalDataEvent : Event`.
Checks in shared Kotlin and on Android work as usual. A Swift factory on iOS checks the Kotlin
event:

```swift
if event.kotlinEvent is PersonalDataEvent {
    return .dropped
}
```

## Errors

Vendor failures go to the error reporter each app sets up: in Kotlin on Android, in Swift on iOS
(on `Herald` and on `HeraldBridge`). To report them through shared Kotlin code, call it from
Swift's reporter:

```swift
let report: AnalyticsErrorReporter = { failure in
    AnalyticsFailuresKt.reportAnalyticsFailure(message: "\(failure)")
}
```

## Documentation

The full documentation is on the [project website](https://mkhytarmkhoian.github.io/herald-docs/).
The [sample](sample) has a shared module, with its tests against the real Android SDK, and an iOS
app that runs it on herald-ios.

See [CHANGELOG.md](CHANGELOG.md) for release notes and [CONTRIBUTING.md](CONTRIBUTING.md) to
contribute.

## License

Apache License 2.0. See [LICENSE](LICENSE).

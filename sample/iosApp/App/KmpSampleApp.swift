import HeraldCore
import HeraldInterop
import HeraldLog
import Shared
import SwiftUI

@main
struct KmpSampleApp: App {
    private let checkout: CheckoutViewModel
    private let profile: ProfileViewModel

    init() {
        // herald-ios, set up in Swift as in any iOS app: two logs, one without personal data.
        let all = Self.log(prefix: "[all]", rules: [])
        let filtered = Self.log(prefix: "[no-personal-data]", rules: [NoPersonalDataLogFactory()])
        let herald = Herald(providers: [
            HeraldProvider(name: "all", events: all, properties: all),
            HeraldProvider(name: "no-personal-data", events: filtered, properties: filtered),
        ])

        // The shared Kotlin view models get herald-ios through the bridge.
        let analytics = IosAnalytics(bridge: HeraldKmpBridge(HeraldBridge(herald)))
        checkout = CheckoutViewModel(analytics: analytics)
        profile = ProfileViewModel(analytics: analytics, properties: analytics)
    }

    var body: some Scene {
        WindowGroup {
            Text("herald-kmp trial")
                .task {
                    try? await checkout.opened()
                    try? await profile.opened(userId: "u42")
                    try? await profile.themeChanged(dark: true)
                    print("KMP TRIAL DONE")
                }
        }
    }

    private static func log(prefix: String, rules: [any LogEventTrackerFactory])
        -> LogAnalyticsTrackerService
    {
        let logger: AnalyticsLogger = { message in print("\(prefix) \(message)") }
        return LogAnalyticsTrackerService(
            eventTrackerFactory: CompositeLogEventTrackerFactory(
                rules + [
                    ScreenViewLogEventTrackerFactory(logger: logger),
                    GenericLogEventTrackerFactory(logger: logger),
                ]),
            propertySetterFactory: GenericLogPropertySetterFactory(logger: logger)
        )
    }
}

import HeraldCore
import HeraldInterop
import HeraldLog
import Shared
import SwiftUI

@main
struct KmpSampleApp: App {
    private let checkout: CheckoutViewModel
    private let profile: ProfileViewModel
    private let store: StoreViewModel

    init() {
        // herald-ios, set up in Swift as in any iOS app: two logs, one without personal data.
        let all = Self.log(prefix: "[all]", rules: [])
        let filtered = Self.log(prefix: "[no-personal-data]", rules: [NoPersonalDataLogFactory()])
        let revenue = LogAnalyticsTrackerService(
            eventTrackerFactory: RevenueLogFactory(logger: { message in print(message) }),
            propertySetterFactory: GenericLogPropertySetterFactory(logger: { _ in })
        )
        let herald = Herald(providers: [
            HeraldProvider(name: "all", events: all, properties: all),
            HeraldProvider(name: "no-personal-data", events: filtered, properties: filtered),
            HeraldProvider(name: "revenue", events: revenue),
        ])

        // The shared Kotlin view models get herald-ios through the bridge. Revenue events become
        // herald-ios's own, so their vendors recognise them.
        let report: AnalyticsErrorReporter = { failure in print("analytics: \(failure)") }
        let bridge = HeraldBridge(herald, translate: revenueEvent, errorReporter: report)
        let analytics = IosAnalytics(bridge: HeraldKmpBridge(bridge))
        checkout = CheckoutViewModel(analytics: analytics)
        profile = ProfileViewModel(analytics: analytics, properties: analytics)
        store = StoreViewModel(analytics: analytics)
    }

    var body: some Scene {
        WindowGroup {
            Text("herald-kmp trial")
                .task {
                    try? await checkout.opened()
                    try? await profile.opened(userId: "u42")
                    try? await profile.themeChanged(dark: true)
                    try? await store.bought(plan: "pro")
                    try? await store.adShown()
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

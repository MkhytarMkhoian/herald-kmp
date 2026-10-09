import HeraldCore
import HeraldInterop
import HeraldLog
import Shared
import SwiftUI

@main
struct KmpSampleApp: App {
    private let viewModel: CheckoutViewModel

    init() {
        // herald-ios, set up in Swift as in any iOS app.
        let logger: AnalyticsLogger = { message in print(message) }
        let log = LogAnalyticsTrackerService(
            eventTrackerFactory: CompositeLogEventTrackerFactory([
                ScreenViewLogEventTrackerFactory(logger: logger),
                GenericLogEventTrackerFactory(logger: logger),
            ]),
            propertySetterFactory: GenericLogPropertySetterFactory(logger: logger)
        )
        let herald = Herald(providers: [HeraldProvider(name: "log", events: log, properties: log)])

        // The shared Kotlin view model gets herald-ios through the bridge.
        let analytics = IosAnalytics(bridge: HeraldKmpBridge(HeraldBridge(herald)))
        viewModel = CheckoutViewModel(analytics: analytics)
    }

    var body: some Scene {
        WindowGroup {
            Text("herald-kmp trial")
                .task {
                    try? await viewModel.opened()
                    try? await viewModel.started(plan: "pro", seats: 2)
                    print("KMP TRIAL DONE")
                }
        }
    }
}

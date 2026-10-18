import HeraldAdjust
import HeraldAmplitude
import HeraldAppsFlyer
import HeraldCore
import HeraldLog
import HeraldMeta

/// Logs only herald-ios's own revenue events: the same type checks the vendors' revenue factories
/// make, so a line here means that vendor would get it as revenue.
struct RevenueLogFactory: LogEventTrackerFactory {
    let logger: AnalyticsLogger

    func create(_ event: any Event) throws -> Resolution<any LogEventTracker> {
        let line: String
        switch event {
        case let revenue as AmplitudeRevenueEvent:
            line =
                "Amplitude revenue \(revenue.name) price=\(revenue.price) "
                + "quantity=\(revenue.quantity) product=\(revenue.productId ?? "-") "
                + "source=\(revenue.parameters["source"]?.asString ?? "-")"
        case let revenue as AdjustRevenueEvent:
            line =
                "Adjust revenue \(revenue.name) \(revenue.revenue) \(revenue.currency) "
                + "dedup=\(revenue.deduplicationId ?? "-")"
        case let revenue as AdjustAdRevenueEvent:
            line =
                "Adjust ad revenue \(revenue.name) source=\(revenue.source) "
                + "network=\(revenue.adRevenueNetwork ?? "-")"
        case let revenue as AppsFlyerPurchaseEvent:
            line =
                "AppsFlyer purchase \(revenue.name) \(revenue.revenue) \(revenue.currency) "
                + "content=\(revenue.contentId ?? "-") quantity=\(revenue.quantity ?? 0)"
        case let revenue as AppsFlyerSubscribeEvent:
            line = "AppsFlyer subscribe \(revenue.name) \(revenue.revenue) \(revenue.currency)"
        case let revenue as AppsFlyerAdRevenueEvent:
            let mediation = revenue.mediationNetwork == .applovinMax ? "AppLovin MAX" : "other"
            line = "AppsFlyer ad revenue \(revenue.name) mediation=\(mediation)"
        case let revenue as MetaPurchaseEvent:
            line = "Meta purchase \(revenue.name) \(revenue.amount) \(revenue.currency)"
        default:
            return .declined
        }
        return .claimed([RevenueLine(text: line, logger: logger)])
    }
}

private struct RevenueLine: LogEventTracker {
    let text: String
    let logger: AnalyticsLogger

    func track() throws { logger("[revenue] \(text)") }
}

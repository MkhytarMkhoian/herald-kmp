import HeraldAmplitude
import HeraldCore
import HeraldLog

/// Logs only herald-ios's own AmplitudeRevenueEvent: the same type check herald-ios-amplitude's
/// revenue factory makes, so a line here means Amplitude would get it as revenue.
struct AmplitudeRevenueLogFactory: LogEventTrackerFactory {
    let logger: AnalyticsLogger

    func create(_ event: any Event) throws -> Resolution<any LogEventTracker> {
        guard let revenue = event as? AmplitudeRevenueEvent else {
            return .declined
        }
        return .claimed([RevenueLine(revenue: revenue, logger: logger)])
    }
}

private struct RevenueLine: LogEventTracker {
    let revenue: AmplitudeRevenueEvent
    let logger: AnalyticsLogger

    func track() throws {
        logger(
            "[amplitude] revenue \(revenue.name) price=\(revenue.price) "
                + "quantity=\(revenue.quantity) product=\(revenue.productId ?? "-") "
                + "currency=\(revenue.currency ?? "-") source=\(revenue.parameters["source"]?.asString ?? "-")"
        )
    }
}

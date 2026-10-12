import HeraldAmplitude
import HeraldCore
import Shared

/// Turns herald-kmp's revenue events into herald-ios's, so each vendor recognises them. Copy this
/// file into your iOS app and keep the blocks for the vendors you use.
@Sendable
func revenueEvent(from kotlinEvent: AnyObject, parameters: [String: AnalyticsValue])
    -> (any Event)?
{
    if let event = kotlinEvent as? KotlinAmplitudeRevenueEvent {
        return AmplitudeRevenueEvent(
            name: event.name, price: event.price, quantity: Int(event.quantity),
            productId: event.productId, revenueType: event.revenueType, currency: event.currency,
            revenue: event.revenue?.doubleValue, receipt: event.receipt,
            receiptSig: event.receiptSig, insertId: event.insertId, parameters: parameters)
    }
    return nil
}

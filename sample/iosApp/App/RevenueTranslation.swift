import AppsFlyerLib
import HeraldAdjust
import HeraldAmplitude
import HeraldAppsFlyer
import HeraldCore
import HeraldMeta
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
    if let event = kotlinEvent as? KotlinAdjustRevenueEvent {
        return AdjustRevenueEvent(
            name: event.name, revenue: event.revenue, currency: event.currency,
            deduplicationId: event.deduplicationId, parameters: parameters)
    }
    if let event = kotlinEvent as? KotlinAdjustAdRevenueEvent {
        return AdjustAdRevenueEvent(
            name: event.name, source: event.source, revenue: event.revenue,
            currency: event.currency, adImpressionsCount: event.adImpressionsCount?.intValue,
            adRevenueNetwork: event.adRevenueNetwork, adRevenueUnit: event.adRevenueUnit,
            adRevenuePlacement: event.adRevenuePlacement, parameters: parameters)
    }
    if let event = kotlinEvent as? KotlinAppsFlyerPurchaseEvent {
        return AppsFlyerPurchaseEvent(
            name: event.name, revenue: event.revenue, currency: event.currency,
            contentId: event.contentId, contentType: event.contentType,
            quantity: event.quantity?.intValue, orderId: event.orderId, parameters: parameters)
    }
    if let event = kotlinEvent as? KotlinAppsFlyerSubscribeEvent {
        return AppsFlyerSubscribeEvent(
            name: event.name, revenue: event.revenue, currency: event.currency,
            parameters: parameters)
    }
    if let event = kotlinEvent as? KotlinAppsFlyerAdRevenueEvent,
        let network = mediationNetworks[event.mediationNetwork.name]
    {
        return AppsFlyerAdRevenueEvent(
            name: event.name, monetizationNetwork: event.monetizationNetwork,
            mediationNetwork: network, revenue: event.revenue, currency: event.currency,
            parameters: parameters)
    }
    if let event = kotlinEvent as? KotlinMetaPurchaseEvent {
        return MetaPurchaseEvent(
            name: event.name, amount: event.amount, currency: event.currency,
            parameters: parameters)
    }
    return nil
}

/// AppsFlyer's mediation networks, by their name in Kotlin. The two SDKs list the same ones.
private let mediationNetworks: [String: MediationNetworkType] = [
    "IRONSOURCE": .ironSource, "APPLOVIN_MAX": .applovinMax, "GOOGLE_ADMOB": .googleAdMob,
    "FYBER": .fyber, "APPODEAL": .appodeal, "ADMOST": .admost, "TOPON": .topon,
    "TRADPLUS": .tradplus, "YANDEX": .yandex, "CHARTBOOST": .chartBoost, "UNITY": .unity,
    "TOPON_PTE": .toponPte, "CUSTOM_MEDIATION": .custom,
    "DIRECT_MONETIZATION_NETWORK": .directMonetization,
]

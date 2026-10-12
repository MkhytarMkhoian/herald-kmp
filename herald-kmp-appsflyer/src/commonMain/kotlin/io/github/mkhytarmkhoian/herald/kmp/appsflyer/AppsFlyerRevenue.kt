package io.github.mkhytarmkhoian.herald.kmp.appsflyer

import io.github.mkhytarmkhoian.herald.kmp.AnalyticsValue
import io.github.mkhytarmkhoian.herald.kmp.Event

// On Android, these are herald-appsflyer's own classes. On iOS, the app's Swift turns them
// into herald-ios-appsflyer's (see RevenueTranslation.swift).

/**
 * A purchase, sent to AppsFlyer as af_purchase revenue. Make one with [appsFlyerPurchaseEvent].
 */
public expect class AppsFlyerPurchaseEvent : Event {
    override val name: String
    public val revenue: Double
    public val currency: String
    public val contentId: String?
    public val contentType: String?
    public val quantity: Int?
    public val orderId: String?
    override val parameters: Map<String, AnalyticsValue>
}

/**
 * A subscription, sent to AppsFlyer as af_subscribe revenue. Make one with
 * [appsFlyerSubscribeEvent].
 */
public expect class AppsFlyerSubscribeEvent : Event {
    override val name: String
    public val revenue: Double
    public val currency: String
    override val parameters: Map<String, AnalyticsValue>
}

/**
 * Ad revenue, sent to AppsFlyer through its ad revenue API. Make one with
 * [appsFlyerAdRevenueEvent].
 */
public expect class AppsFlyerAdRevenueEvent : Event {
    override val name: String
    public val monetizationNetwork: String
    public val mediationNetwork: MediationNetwork
    public val revenue: Double
    public val currency: String
    override val parameters: Map<String, AnalyticsValue>
}

/**
 * The ad mediation network. AppsFlyer's Android and iOS SDKs list the same ones; on Android this is
 * the SDK's own enum.
 */
public expect enum class MediationNetwork {
    IRONSOURCE,
    APPLOVIN_MAX,
    GOOGLE_ADMOB,
    FYBER,
    APPODEAL,
    ADMOST,
    TOPON,
    TRADPLUS,
    YANDEX,
    CHARTBOOST,
    UNITY,
    TOPON_PTE,
    CUSTOM_MEDIATION,
    DIRECT_MONETIZATION_NETWORK,
}

/**
 * Makes an [AppsFlyerPurchaseEvent], with the same parameters and defaults as Android's
 * constructor. Shared code can't use the constructor itself: Kotlin drops its defaults.
 */
public expect fun appsFlyerPurchaseEvent(
    name: String,
    revenue: Double,
    currency: String,
    contentId: String? = null,
    contentType: String? = null,
    quantity: Int? = null,
    orderId: String? = null,
    parameters: Map<String, AnalyticsValue> = emptyMap(),
): AppsFlyerPurchaseEvent

/**
 * Makes an [AppsFlyerSubscribeEvent], with the same parameters and defaults as Android's
 * constructor. Shared code can't use the constructor itself: Kotlin drops its defaults.
 */
public expect fun appsFlyerSubscribeEvent(
    name: String,
    revenue: Double,
    currency: String,
    parameters: Map<String, AnalyticsValue> = emptyMap(),
): AppsFlyerSubscribeEvent

/**
 * Makes an [AppsFlyerAdRevenueEvent], with the same parameters and defaults as Android's
 * constructor. Shared code can't use the constructor itself: Kotlin drops its defaults.
 */
public expect fun appsFlyerAdRevenueEvent(
    name: String,
    monetizationNetwork: String,
    mediationNetwork: MediationNetwork,
    revenue: Double,
    currency: String,
    parameters: Map<String, AnalyticsValue> = emptyMap(),
): AppsFlyerAdRevenueEvent

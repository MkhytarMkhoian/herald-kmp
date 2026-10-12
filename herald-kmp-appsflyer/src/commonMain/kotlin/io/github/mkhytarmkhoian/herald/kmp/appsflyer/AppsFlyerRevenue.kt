package io.github.mkhytarmkhoian.herald.kmp.appsflyer

import io.github.mkhytarmkhoian.herald.kmp.AnalyticsValue
import io.github.mkhytarmkhoian.herald.kmp.Event

/**
 * A purchase, sent to AppsFlyer as af_purchase revenue. On Android it is herald-appsflyer's own
 * class; on iOS the app's Swift turns it into herald-ios-appsflyer's. Shared code makes one with
 * [appsFlyerPurchaseEvent].
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
 * A subscription, sent to AppsFlyer as af_subscribe revenue. On Android it is herald-appsflyer's
 * own class; on iOS the app's Swift turns it into herald-ios-appsflyer's. Shared code makes one
 * with [appsFlyerSubscribeEvent].
 */
public expect class AppsFlyerSubscribeEvent : Event {
    override val name: String
    public val revenue: Double
    public val currency: String
    override val parameters: Map<String, AnalyticsValue>
}

/**
 * Ad revenue, sent to AppsFlyer through its ad revenue API. On Android it is herald-appsflyer's own
 * class; on iOS the app's Swift turns it into herald-ios-appsflyer's. Shared code makes one with
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
 * The ad mediation network: the same list as in AppsFlyer's Android and iOS SDKs. On Android it is
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
 * constructor. Shared code can't call that constructor: Kotlin drops its default values on the way
 * from Android.
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
 * constructor. Shared code can't call that constructor: Kotlin drops its default values on the way
 * from Android.
 */
public expect fun appsFlyerSubscribeEvent(
    name: String,
    revenue: Double,
    currency: String,
    parameters: Map<String, AnalyticsValue> = emptyMap(),
): AppsFlyerSubscribeEvent

/**
 * Makes an [AppsFlyerAdRevenueEvent], with the same parameters and defaults as Android's
 * constructor. Shared code can't call that constructor: Kotlin drops its default values on the way
 * from Android.
 */
public expect fun appsFlyerAdRevenueEvent(
    name: String,
    monetizationNetwork: String,
    mediationNetwork: MediationNetwork,
    revenue: Double,
    currency: String,
    parameters: Map<String, AnalyticsValue> = emptyMap(),
): AppsFlyerAdRevenueEvent

@file:OptIn(ExperimentalObjCName::class)

package io.github.mkhytarmkhoian.herald.kmp.appsflyer

import io.github.mkhytarmkhoian.herald.kmp.AnalyticsValue
import io.github.mkhytarmkhoian.herald.kmp.IosVendorEvent
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

// herald-ios-appsflyer has classes with these names too, so Swift sees these with
// "Kotlin" in front. IosVendorEvent tells the bridge the app must translate them.

@ObjCName(swiftName = "KotlinAppsFlyerPurchaseEvent")
public actual class AppsFlyerPurchaseEvent internal constructor(
    actual override val name: String,
    public actual val revenue: Double,
    public actual val currency: String,
    public actual val contentId: String?,
    public actual val contentType: String?,
    public actual val quantity: Int?,
    public actual val orderId: String?,
    actual override val parameters: Map<String, AnalyticsValue>,
) : IosVendorEvent

@ObjCName(swiftName = "KotlinAppsFlyerSubscribeEvent")
public actual class AppsFlyerSubscribeEvent internal constructor(
    actual override val name: String,
    public actual val revenue: Double,
    public actual val currency: String,
    actual override val parameters: Map<String, AnalyticsValue>,
) : IosVendorEvent

@ObjCName(swiftName = "KotlinAppsFlyerAdRevenueEvent")
public actual class AppsFlyerAdRevenueEvent internal constructor(
    actual override val name: String,
    public actual val monetizationNetwork: String,
    public actual val mediationNetwork: MediationNetwork,
    public actual val revenue: Double,
    public actual val currency: String,
    actual override val parameters: Map<String, AnalyticsValue>,
) : IosVendorEvent

public actual enum class MediationNetwork {
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

public actual fun appsFlyerPurchaseEvent(
    name: String,
    revenue: Double,
    currency: String,
    contentId: String?,
    contentType: String?,
    quantity: Int?,
    orderId: String?,
    parameters: Map<String, AnalyticsValue>,
): AppsFlyerPurchaseEvent = AppsFlyerPurchaseEvent(
    name = name,
    revenue = revenue,
    currency = currency,
    contentId = contentId,
    contentType = contentType,
    quantity = quantity,
    orderId = orderId,
    parameters = parameters,
)

public actual fun appsFlyerSubscribeEvent(
    name: String,
    revenue: Double,
    currency: String,
    parameters: Map<String, AnalyticsValue>,
): AppsFlyerSubscribeEvent = AppsFlyerSubscribeEvent(
    name = name,
    revenue = revenue,
    currency = currency,
    parameters = parameters,
)

public actual fun appsFlyerAdRevenueEvent(
    name: String,
    monetizationNetwork: String,
    mediationNetwork: MediationNetwork,
    revenue: Double,
    currency: String,
    parameters: Map<String, AnalyticsValue>,
): AppsFlyerAdRevenueEvent = AppsFlyerAdRevenueEvent(
    name = name,
    monetizationNetwork = monetizationNetwork,
    mediationNetwork = mediationNetwork,
    revenue = revenue,
    currency = currency,
    parameters = parameters,
)

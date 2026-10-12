package io.github.mkhytarmkhoian.herald.kmp.appsflyer

import io.github.mkhytarmkhoian.herald.kmp.AnalyticsValue

public actual typealias AppsFlyerPurchaseEvent =
    io.github.mkhytarmkhoian.herald.appsflyer.AppsFlyerPurchaseEvent

public actual typealias AppsFlyerSubscribeEvent =
    io.github.mkhytarmkhoian.herald.appsflyer.AppsFlyerSubscribeEvent

public actual typealias AppsFlyerAdRevenueEvent =
    io.github.mkhytarmkhoian.herald.appsflyer.AppsFlyerAdRevenueEvent

public actual typealias MediationNetwork = com.appsflyer.share.MediationNetwork

public actual fun appsFlyerPurchaseEvent(
    name: String,
    revenue: Double,
    currency: String,
    contentId: String?,
    contentType: String?,
    quantity: Int?,
    orderId: String?,
    parameters: Map<String, AnalyticsValue>,
): AppsFlyerPurchaseEvent = io.github.mkhytarmkhoian.herald.appsflyer.AppsFlyerPurchaseEvent(
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
): AppsFlyerSubscribeEvent = io.github.mkhytarmkhoian.herald.appsflyer.AppsFlyerSubscribeEvent(
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
): AppsFlyerAdRevenueEvent = io.github.mkhytarmkhoian.herald.appsflyer.AppsFlyerAdRevenueEvent(
    name = name,
    monetizationNetwork = monetizationNetwork,
    mediationNetwork = mediationNetwork,
    revenue = revenue,
    currency = currency,
    parameters = parameters,
)

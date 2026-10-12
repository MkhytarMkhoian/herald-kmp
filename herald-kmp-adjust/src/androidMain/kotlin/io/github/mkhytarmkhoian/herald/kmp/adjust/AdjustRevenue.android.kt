package io.github.mkhytarmkhoian.herald.kmp.adjust

import io.github.mkhytarmkhoian.herald.kmp.AnalyticsValue

public actual typealias AdjustRevenueEvent =
    io.github.mkhytarmkhoian.herald.adjust.AdjustRevenueEvent

public actual typealias AdjustAdRevenueEvent =
    io.github.mkhytarmkhoian.herald.adjust.AdjustAdRevenueEvent

public actual fun adjustRevenueEvent(
    name: String,
    revenue: Double,
    currency: String,
    deduplicationId: String?,
    parameters: Map<String, AnalyticsValue>,
): AdjustRevenueEvent = io.github.mkhytarmkhoian.herald.adjust.AdjustRevenueEvent(
    name = name,
    revenue = revenue,
    currency = currency,
    deduplicationId = deduplicationId,
    parameters = parameters,
)

public actual fun adjustAdRevenueEvent(
    name: String,
    source: String,
    revenue: Double,
    currency: String,
    adImpressionsCount: Int?,
    adRevenueNetwork: String?,
    adRevenueUnit: String?,
    adRevenuePlacement: String?,
    parameters: Map<String, AnalyticsValue>,
): AdjustAdRevenueEvent = io.github.mkhytarmkhoian.herald.adjust.AdjustAdRevenueEvent(
    name = name,
    source = source,
    revenue = revenue,
    currency = currency,
    adImpressionsCount = adImpressionsCount,
    adRevenueNetwork = adRevenueNetwork,
    adRevenueUnit = adRevenueUnit,
    adRevenuePlacement = adRevenuePlacement,
    parameters = parameters,
)
